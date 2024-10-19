package Optimize;

import IR.IRBlock;
import IR.IRProgram;
import IR.instr.*;
import IR.module.IRFuncDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Mem2Reg {
    public IRProgram program;
    public IRFuncDef curFunc;
    public HashSet<String>AllocaVar;//Alloca出来的局部变量
    public HashMap<String,HashMap<IRBlock,String>>deflist;//每个局部变量在每个块中的最后一次def
    public HashMap<String, Stack<String>>valuestack;//每个变量名的栈顶就是当前的变量值
    public HashMap<String,String>replaceMap;//记录每个变量应该被重命名为什么值

    public Mem2Reg(IRProgram program) {
        this.program = program;
        init();
    }

    public void init(){
        AllocaVar = new HashSet<>();
        deflist = new HashMap<>();
        valuestack = new HashMap<>();
        replaceMap = new HashMap<>();
    }

    public void work(){
        for(var func:program.funcs){
            init();
            visitFunc(func);
        }
    }

    public void visitFunc(IRFuncDef func){
        curFunc = func;
        var allocaList = func.getAlloca();
        for(var alloca:allocaList){
            AllocaVar.add(alloca.first);
            deflist.put(alloca.first,new HashMap<>());
            valuestack.put(alloca.first,new Stack<>());
        }
        //放置phi
        for(var alloca:allocaList){
            placePhi(alloca.first,alloca.second);
        }
        //变量重命名
        replace();
    }

    //对于一个Alloca的局部变量，在所有def块的支配边界头部放置phi，再对预留了phi的块的支配边界头部放置phi，重复操作。
    //采用工作表算法
    public void placePhi(String name,String type){
        HashSet<IRBlock>WorkList = new HashSet<>();//防止重复放置，用HashSet
        //考虑entry
        for(var instr:curFunc.entry.statements){
            if(instr instanceof Store && ((Store) instr).pointer.equals(name)){//def
                deflist.get(name).put(curFunc.entry,((Store) instr).value);//更新，保存局部变量在块内的最后一次def
                WorkList.add(curFunc.entry);
            }
        }
        if(curFunc.entry.terminalStmt instanceof Store && ((Store) curFunc.entry.terminalStmt).pointer.equals(name)){
            deflist.get(name).put(curFunc.entry,((Store) curFunc.entry.terminalStmt).value);
            WorkList.add(curFunc.entry);
        }
        //其余块
        for(var block:curFunc.body){
            for(var instr:block.statements){
                if(instr instanceof Store && ((Store) instr).pointer.equals(name)){//def
                    deflist.get(name).put(block,((Store) instr).value);//更新，保存局部变量在块内的最后一次def
                    WorkList.add(block);
                }
            }
            if(block.terminalStmt instanceof Store && ((Store) block.terminalStmt).pointer.equals(name)){
                deflist.get(name).put(block,((Store) block.terminalStmt).value);
                WorkList.add(block);
            }
        }

        while(!WorkList.isEmpty()){
            IRBlock curBlock = WorkList.iterator().next();
            WorkList.remove(curBlock);
            if(!deflist.get(name).containsKey(curBlock)){//phi指令也算一次def，更新deflist
                deflist.get(name).put(curBlock,name+'.'+curBlock.label);
            }
            for(var domF:curBlock.domFrontier){
                if(!domF.philist.containsKey(name)){//如果该块中没有预留对应变量的phi指令
                    //Phi指令结果命名:变量名+块label
                    Phi ins = new Phi(name+'.'+domF.label,type);
                    int i=0;
                    for(var pred:domF.pred){//加入所有CFG上前驱
                        ins.labels.add(pred.label);
                        ins.label_order.put(pred.label,i++);
                    }
                    domF.philist.put(name,ins);
                    WorkList.add(domF);
                }
            }
        }
    }

    public void rename(IRBlock block,IRBlock preBlock){
        //创建一个副本，用于在函数末恢复
        HashMap<String,Stack<String>>copy = new HashMap<>();
        for(String key:valuestack.keySet()){
            Stack<String> newStack = (Stack<String>) valuestack.get(key).clone();
            copy.put(key,newStack);
        }
        //所有phi语句也算def,也要更新last_def
        for(var phi:block.philist.keySet()){
            var value = valuestack.get(phi).peek();
            var phiIns = block.philist.get(phi);
            int order = phiIns.label_order.get(preBlock.label);
            if(value == null){
                if(phiIns.ty.equals("ptr")){
                    phiIns.vals.set(order,"null");
                }else{
                    phiIns.vals.set(order,"0");
                }
            }else{
                phiIns.vals.set(order,value);
            }
            valuestack.get(phi).push(phiIns.result);
        }

        ArrayList<Instruction>deletelist = new ArrayList<>();
        for(var instr:block.statements){
            if(instr instanceof Load){
                String name = ((Load) instr).pointer;
                if(AllocaVar.contains(name)){
                    String cur_value = valuestack.get(name).pop();
                    deletelist.add(instr);
                    replaceMap.put(((Load) instr).result,cur_value);
                }
            }else if(instr instanceof Store){
                String name = ((Store) instr).pointer;
                if(AllocaVar.contains(name)){
                    deletelist.add(instr);
                    valuestack.get(name).push(((Store) instr).value);
                }
            }else if(instr instanceof Alloca){
                deletelist.add(instr);
            }
        }
        block.statements.removeAll(deletelist);
        //用当前值更新CFG上所有后继中的phi
//        for(var succ:block.succ){
//            for(var element:succ.philist.keySet()){
//                if()
//            }
//        }
        for(var succ:block.succ){
            rename(succ,block);
            valuestack = copy;
        }
    }

    public void replace(){
        for(var instr:curFunc.entry.statements){
            instr.rename(replaceMap);
        }
        curFunc.entry.terminalStmt.rename(replaceMap);
        for(var block:curFunc.body){
            for(var instr:block.statements){
                instr.rename(replaceMap);
            }
            block.terminalStmt.rename(replaceMap);
        }
    }
}
