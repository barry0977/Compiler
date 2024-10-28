package Optimize;

import IR.IRBlock;
import IR.IRProgram;
import IR.instr.*;
import IR.module.IRFuncDef;

import java.beans.Visibility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CFGBuilder {
    public IRProgram program;
    public IRFuncDef curFunc;

    public CFGBuilder(IRProgram program){
        this.program = program;
    }

    public void work(){
        for(var func:program.funcs){
            visitFunc(func);
        }
    }

    public void visitBlock(IRBlock it){
        if(it.terminalStmt instanceof Br){
            if(((Br) it.terminalStmt).haveCondition){//条件跳转
                String truedest = ((Br) it.terminalStmt).iftrue, falsedest = ((Br) it.terminalStmt).iffalse;
                for(var block:curFunc.body){
                    if(block.label.equals(truedest)){
                        it.succ.add(block);
                        block.pred.add(it);
                    }else if(block.label.equals(falsedest)){
                        it.succ.add(block);
                        block.pred.add(it);
                        break;
                    }
                }
            }else{//无条件跳转
                String dest = ((Br) it.terminalStmt).dest;
                for(var block:curFunc.body){
                    if(block.label.equals(dest)){//要跳转的块
                        block.pred.add(it);
                        it.succ.add(block);
                        break;
                    }
                }
            }
        }else if(it.terminalStmt instanceof Ret){
            return;
        }
    }

    public void visitFunc(IRFuncDef it){
        HashSet<IRBlock> visited=new HashSet<>();
        curFunc = it;
        visitBlock(it.entry);
        for(var block:curFunc.body){
            visitBlock(block);
        }

        dfsEntry(curFunc.entry,visited);
        //去除entry块无法到达的节点
//        ArrayList<IRBlock>deletelist = new ArrayList<>();
//        for(var block:curFunc.body){
//            if(!visited.contains(block)){
//                deletelist.add(block);
//            }
//        }
        //去除entry无法到达的结点,即既不是entry,也没有前驱的block
        for(var block:curFunc.body){
            if(block.pred.isEmpty()){
                for(var suc:block.succ){
                    suc.pred.remove(block);
                }
            }
        }
        //先把这些节点加到一个列表中再删除，否则边遍历边删除会出现ConcurrentModificationException
        ArrayList<IRBlock>deletelist = new ArrayList<>();
        for(var block:curFunc.body){
            if(block.pred.isEmpty()){
                deletelist.add(block);
            }
        }
        it.body.removeAll(deletelist);
    }

    public void dfsEntry(IRBlock block,HashSet<IRBlock> visited){
        visited.add(block);
        for(var succ:block.succ){
            if(!visited.contains(succ)){
                dfsEntry(succ, visited);
            }
        }
    }
}

