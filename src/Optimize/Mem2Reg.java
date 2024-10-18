package Optimize;

import IR.IRProgram;
import IR.module.IRFuncDef;

public class Mem2Reg {
    public IRProgram program;

    public Mem2Reg(IRProgram program) {
        this.program = program;
    }

    public void work(){
        for(var func:program.funcs){
            visitFunc(func);
        }
    }

    public void visitFunc(IRFuncDef func){

    }
}
