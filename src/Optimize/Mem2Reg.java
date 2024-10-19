package Optimize;

import IR.IRBlock;
import IR.IRProgram;
import IR.instr.*;
import IR.module.IRFuncDef;

import java.util.HashMap;
import java.util.HashSet;

public class Mem2Reg {
    public IRProgram program;
    public IRFuncDef curFunc;
    public HashMap<String,String>allocavar;//所有Alloca变量名和类型
    public HashMap<String,HashMap<IRBlock,String>>deflist;//每个局部变量在每个块中的最后一次def
    public HashMap<String,String>valueStack;

    public Mem2Reg(IRProgram program) {
        this.program = program;
        allocavar = new HashMap<>();
        deflist = new HashMap<>();
        valueStack = new HashMap<>();
    }

    public void work(){
        for(var func:program.funcs){
            visitFunc(func);
        }
    }

    public void visitFunc(IRFuncDef func){
        curFunc = func;
        var allocaList = func.getAlloca();
        for(var alloca:allocaList){
            allocavar.put(alloca.first,alloca.second);
            deflist.put(alloca.first,new HashMap<>());
        }
        //放置phi
        for(var alloca:allocaList){
            placePhi(alloca.first,alloca.second);
        }
        //变量重命名

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
                    for(var pred:domF.pred){//加入所有CFG上前驱
                        ins.labels.add(pred.label);
                    }
                    domF.philist.put(name,ins);
                    WorkList.add(domF);
                }
            }
        }
    }
}
