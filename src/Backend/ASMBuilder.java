package Backend;

import ASM.ASMBlock;
import ASM.ASMProgram;
import ASM.instr.*;
import ASM.module.*;
import ASM.util.*;
import IR.IRBlock;
import IR.IRProgram;
import IR.IRVisitor;
import IR.instr.*;
import IR.module.*;

public class ASMBuilder implements IRVisitor {
    public ASMProgram program;
    public ASMBlock curBlock;
    public ASMFuncDef curFunc;


    public ASMBuilder(ASMProgram _program) {
        this.program = _program;
    }

    //lw和sw的立即数只有12位，只能支持-2048~2047
    //如果超出范围，将立即数存在t6寄存器中，再进行运算
    public void AddLoad(ASMRegister rd, ASMAddr addr){
        int offset=addr.offset;
        if(offset<=2047&&offset>=-2048){
            curBlock.addIns(new ASMlw(rd,addr));
        }else{
            curBlock.addIns(new ASMli(new ASMRegister("t6"),offset));
            curBlock.addIns(new ASMarith("add",new ASMRegister("t6"),new ASMRegister("t6"),addr.reg));
            curBlock.addIns(new ASMlw(rd,new ASMAddr(new ASMRegister("t6"),0)));
        }
    }

    public void AddStore(ASMRegister rd, ASMAddr addr){
        int offset=addr.offset;
        if(offset<=2047&&offset>=-2048){
            curBlock.addIns(new ASMsw(rd,addr));
        }else{
            curBlock.addIns(new ASMli(new ASMRegister("t6"),offset));
            curBlock.addIns(new ASMarith("add",new ASMRegister("t6"),new ASMRegister("t6"),addr.reg));
            curBlock.addIns(new ASMsw(rd,new ASMAddr(new ASMRegister("t6"),0)));
        }
    }

    public ASMRegister loadReg(String obj,ASMRegister rd){
        if(obj.charAt(0)=='@'){//全局变量
            curBlock.addIns(new ASMla(rd,obj));
        }else if(obj.charAt(0)=='%'){//局部变量
            var id=curFunc.args_ord.get(obj);
            if(id==null){//局部变量
                //TODO
            }else{//参数
                if(id<8){//存在寄存器中
                }else{//存在stack中

                }
            }
        }else{//常量
            curBlock.addIns(new ASMli(rd,Integer.parseInt(obj)));
        }
        return new ASMRegister("t0");
    }


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
        if(it.terminalStmt!=null){
            it.terminalStmt.accept(this);
        }
    }

    public void visit(IRClassDef it){}

    //不需要函数声明
    public void visit(IRFuncDecl it){}

    public void visit(IRFuncDef it){
        ASMFuncDef Function=new ASMFuncDef(it.name,it.paramnames.size());
        program.funcs.add(Function);
        curFunc=Function;

        for(int i=0;i<it.paramnames.size();i++){
            Function.args_ord.put(it.paramnames.get(i),i);
        }

        curBlock=curFunc.addBlock(new ASMBlock(curFunc.name+"."+"entry"));
        it.entry.accept(this);
        for(var block:it.body){
            curBlock=curFunc.addBlock(new ASMBlock(curFunc.name+"."+block.label));
            block.accept(this);
        }
    }

    public void visit(IRGlobalVarDef it){
        program.globalvars.add(new ASMGlobalVarDef(it.name,it.value));
    }

    public void visit(IRStringDef it){
        program.strs.add(new ASMStringDef(".str."+it.label,it.value,it.length+1));
    }

    public void visit(Alloca it){
        //TODO
    }

    public void visit(Binary it){
        var reg1=new ASMRegister("t0");
        var reg2=new ASMRegister("t1");
        var reg3=new ASMRegister("t2");

        loadReg(it.lhs,reg1);
        loadReg(it.rhs,reg2);

        String op=it.op;
        switch(op){
            case "add":op="add";break;
            case "sub":op="sub";break;
            case "mul":op="mul";break;
            case "sdiv":op="div";break;
            case "srem":op="rem";break;
            case "shl":op="sll";break;
            case "ashr":op="sra";break;
            case "and":op="and";break;
            case "or":op="or";break;
            case "xor":op="xor";break;
        }

        curBlock.addIns(new ASMarith(op,reg1,reg2,reg3));
        //TODO
    }

    public void visit(Br it){
        if(it.haveCondition){
            var reg1=new ASMRegister("t0");
            String truelabel=curFunc.name+"."+it.iftrue;
            String falselabel=curFunc.name+"."+it.iffalse;
            loadReg(it.cond,reg1);
            curBlock.addIns(new ASMbranch(""));
        }else{
            curBlock.addIns(new ASMj(curFunc.name+"."+it.dest));
        }
    }

    public void visit(Call it){
        //TODO
    }

    public void visit(Getelementptr it){
        //TODO
    }

    public void visit(Icmp it){
        //用t0，t1保存两个操作数
        var reg1=new ASMRegister("t0");
        var reg2=new ASMRegister("t1");
        var reg3=new ASMRegister("t2");

        loadReg(it.op1,reg1);
        loadReg(it.op2,reg2);
        curBlock.addIns(new ASMarith("sub",reg1,reg2,reg3));
        var op=it.cond;
        if(op.equals("eq")){//==
            curBlock.addIns(new ASMset(reg3,reg3,"seqz"));
        }else if(op.equals("ne")){//!=
            curBlock.addIns(new ASMset(reg3,reg3,"snez"));
        }else if(op.equals("sgt")){//>
            curBlock.addIns(new ASMset(reg3,reg3,"sgtz"));
        }else if(op.equals("sge")){//>=
            //reg1>reg2 || reg1==reg2
            curBlock.addIns(new ASMset(reg1,reg3,"sgtz"));
            curBlock.addIns(new ASMset(reg2,reg3,"seqz"));
            curBlock.addIns(new ASMarith("or",reg1,reg2,reg3));
        }else if(op.equals("slt")){//<
            curBlock.addIns(new ASMset(reg3,reg3,"sltz"));
        }else if(op.equals("sle")){//<=
            //reg1<reg2 || reg1==reg2
            curBlock.addIns(new ASMset(reg1,reg3,"sltz"));
            curBlock.addIns(new ASMset(reg2,reg3,"seqz"));
            curBlock.addIns(new ASMarith("or",reg1,reg2,reg3));

        }
        //TODO
    }

    public void visit(Load it){
        //TODO
    }

    public void visit(Phi it){}

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
