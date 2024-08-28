package Backend;

import ASM.ASMBlock;
import ASM.ASMProgram;
import ASM.module.ASMFuncDef;
import ASM.module.ASMGlobalVarDef;
import IR.IRBlock;
import IR.IRProgram;
import IR.IRVisitor;
import IR.instr.*;
import IR.module.*;

public class ASMBuilder implements IRVisitor {
    public ASMProgram program;
    public ASMBlock curBlock;


    public ASMBuilder(ASMProgram _program) {
        this.program = _program;
    }


    public void

    public void visit(IRProgram it){
        for(var funcDef:it.funcs){
            funcDef.accept(this);
        }
        for(var varDef:it.globalvars){
            varDef.accept(this);
        }
    }

    public void visit(IRBlock it){
        if(!it.label.equals("entry")){

        }
        for(var ins:it.statements){
            ins.accept(this);
        }
        it.terminalStmt.accept(this);
    }

    public void visit(IRClassDef it){}

    public void visit(IRFuncDef it){
        ASMFuncDef Function=new ASMFuncDef();

        for(int i=0;i<it.paramnames.size();i++){
            if(i<8){

            }else{

            }
        }
        it.entry.accept(this);
        for(var block:it.body){
            block.accept(this);
        }
    }

    public void visit(IRGlobalVarDef it){
        program.globalvars.add(new ASMGlobalVarDef(it.name,it.value));
    }

    public void visit(IRStringDef it){
        program.strs.add(new )
    }

    public void visit(Alloca it){
        //TODO
    }

    public void visit(Binary it){

        //TODO
    }

    public void visit(Br it){
        //TODO
    }

    public void visit(Call it){
        //TODO
    }

    public void visit(Getelementptr it){
        //TODO
    }

    public void visit(Icmp it){
        //TODO
    }

    public void visit(Load it){
        //TODO
    }

    public void visit(Phi it){
        //TODO
    }

    public void visit(Ret it){
        //TODO
    }

    public void visit(Select it){
        //TODO
    }

    public void visit(Store it){
        //TODO
    }

}
