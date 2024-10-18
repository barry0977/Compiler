package Optimize;

import IR.IRBlock;
import IR.IRProgram;
import IR.instr.*;
import IR.module.IRFuncDef;

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
                    }
                }
            }else{//无条件跳转
                String dest = ((Br) it.terminalStmt).dest;
                for(var block:curFunc.body){
                    if(block.label.equals(dest)){//要跳转的块
                        block.pred.add(it);
                        it.succ.add(it);
                        break;
                    }
                }
            }
        }else if(it.terminalStmt instanceof Ret){
            return;
        }
    }

    public void visitFunc(IRFuncDef it){
        curFunc = it;
        visitBlock(it.entry);
        for(var block:curFunc.body){
            visitBlock(block);
        }
        //去除entry无法到达的结点,即既不是entry,也没有前驱的block
        for(var block:curFunc.body){
            if(block.pred.isEmpty()){
                for(var suc:block.succ){
                    suc.pred.remove(block);
                }
            }
        }
        for(var block:curFunc.body){
            if(block.pred.isEmpty()){
                it.body.remove(block);
            }
        }
    }
}
