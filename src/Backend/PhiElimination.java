package Backend;

import IR.IRBlock;
import IR.module.IRFuncDef;

public class PhiElimination {
    public IRFuncDef func;

    public PhiElimination(IRFuncDef func) {
        this.func = func;
    }

    public void work(){
        for(var block:func.body){
            visitBlock(block);
        }
    }

    public void visitBlock(IRBlock block){

    }
}
