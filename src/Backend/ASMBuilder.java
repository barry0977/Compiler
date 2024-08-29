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

    //给每个虚拟寄存器分配内存(即有返回值的指令)
    public void SetStack(Instruction ins){
        if(ins instanceof Alloca){//Alloca本身需要分配内存
            curFunc.stacksize+=4;
            curFunc.var_ord.put(((Alloca) ins).result,++curFunc.varcnt);
        }else if(ins instanceof Binary){
            curFunc.stacksize+=4;
            curFunc.var_ord.put(((Binary) ins).result,++curFunc.varcnt);
        }else if(ins instanceof Call){
            if(!((Call) ins).ResultType.equals("void")){
                curFunc.stacksize+=4;
                curFunc.var_ord.put(((Call) ins).result,++curFunc.varcnt);
            }
            curFunc.callArgsCnt=Math.max(curFunc.callArgsCnt,((Call) ins).ArgsVal.size());//计算函数调用参数个数的最大值，用来分配空间
        }else if(ins instanceof Getelementptr){
            curFunc.stacksize+=4;
            curFunc.var_ord.put(((Getelementptr) ins).result,++curFunc.varcnt);
        }else if(ins instanceof Icmp){
            curFunc.stacksize+=4;
            curFunc.var_ord.put(((Icmp) ins).result,++curFunc.varcnt);
        }else if(ins instanceof Load){
            curFunc.stacksize+=4;
            curFunc.var_ord.put(((Load) ins).result,++curFunc.varcnt);
        }else if(ins instanceof Select){
            curFunc.stacksize+=4;
            curFunc.var_ord.put(((Select) ins).result,++curFunc.varcnt);
        }
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

    //addi的立即数也只有12位
    public void AddAddi(ASMRegister rd,ASMRegister rs1,int imm){
        if(imm<=2047&&imm>=-2048){
            curBlock.addIns(new ASMarithimm("addi",rs1,rd,imm));
        }else{
            curBlock.addIns(new ASMli(new ASMRegister("t6"),imm));
            curBlock.addIns(new ASMarith("add",rs1,new ASMRegister("t6"),rd));
        }
    }

    //把一个值load到寄存器中
    public ASMRegister loadReg(String obj,ASMRegister rd){
        if(obj.charAt(0)=='@'){//全局变量
            curBlock.addIns(new ASMla(rd,obj));
        }else if(obj.charAt(0)=='%'){//局部变量
            ASMRegister sp=new ASMRegister("sp");
            var id=curFunc.args_ord.get(obj);
            if(id==null){//局部变量
                int offset=curFunc.getVar_offset(obj);
                AddLoad(rd,new ASMAddr(sp,offset));
            }else{//参数
                if(id<8){//存在寄存器中
                    ASMRegister rs=new ASMRegister("a"+id);
                    curBlock.addIns(new ASMmv(rd,rs));
                }else{//存在stack中
                    int offset=curFunc.getArgs_offset(id);
                    ASMAddr addr=new ASMAddr(sp,offset);
                    AddLoad(rd,addr);
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
            curBlock=curFunc.addBlock(new ASMBlock(curFunc.name+"."+it.label));
        }
        for(var ins:it.statements){
            ins.accept(this);
        }
        if(it.terminalStmt!=null){
            it.terminalStmt.accept(this);
        }
    }

    //不需要类的声明
    public void visit(IRClassDef it){}

    //不需要函数声明
    public void visit(IRFuncDecl it){}

    public void visit(IRFuncDef it){
        ASMFuncDef Function=new ASMFuncDef(it.name,it.paramnames.size());
        program.funcs.add(Function);
        curFunc=Function;

        for(int i=0;i<it.paramnames.size();i++){//传入参数
            Function.args_ord.put(it.paramnames.get(i),i);
        }

        //确定栈的大小,为所有变量分配空间
        //terminalStmt只会跳转，不会产生新的变量，不需要访问
        for(var ins:it.entry.statements){
            SetStack(ins);
        }
        for(var block:it.body){
            for(var ins:block.statements){
                SetStack(ins);
            }
        }

        curFunc.stacksize+=4*curFunc.callArgsCnt;
        curFunc.stacksize+=4;//再为ra创建空间
        //stacksize必须是16的整数倍
        if(curFunc.stacksize%16!=0){
            curFunc.stacksize=(curFunc.stacksize/16+1)*16;
        }

        curBlock=curFunc.addBlock(new ASMBlock(it.name));//创建入口块
        var sp=new ASMRegister("sp");
        var ra=new ASMRegister("ra");
        AddAddi(sp,sp, -curFunc.stacksize);//addi sp,sp,-stacksize
        AddStore(ra,new ASMAddr(sp,curFunc.stacksize-4));//sw ra,stacksize-4(sp)

        it.entry.accept(this);
        for(var block:it.body){
            block.accept(this);
        }
    }

    public void visit(IRGlobalVarDef it){
        program.globalvars.add(new ASMGlobalVarDef(it.name,it.value));
    }

    public void visit(IRStringDef it){
        program.strs.add(new ASMStringDef(".str."+it.label,it.origin,it.length+1));
    }

    //前面已经用栈分配了空间，不用进行操作
    public void visit(Alloca it){}

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
        int offset=curFunc.getVar_offset(it.result);
        AddStore(reg3,new ASMAddr(new ASMRegister("sp"),offset));
    }


    public void visit(Br it){
        if(it.haveCondition){
            var reg1=new ASMRegister("t0");
            String truelabel=curFunc.name+"."+it.iftrue;
            String falselabel=curFunc.name+"."+it.iffalse;
            loadReg(it.cond,reg1);
            curBlock.addIns(new ASMbranch(reg1,truelabel,"bnez"));//如果条件正确，则跳转到truelabel
            curBlock.addIns(new ASMj(falselabel));//否则直接跳转到falselabel
        }else{
            curBlock.addIns(new ASMj(curFunc.name+"."+it.dest));
        }
    }

    //调用者保存a0-a7，调用完再还原
    public void visit(Call it){
        var reg1=new ASMRegister("t0");//用于临时存储参数
        var sp=new ASMRegister("sp");
        //先把会占用的a0-a7存起来
        for(int i=0;i<Math.min(8,it.ArgsVal.size());i++){
            String reg_name="a"+i;
            AddStore(new ASMRegister(reg_name),new ASMAddr(sp,curFunc.getArgs_offset(i)));
        }
        for(int i=0;i<it.ArgsVal.size();i++){
            loadReg(it.ArgsVal.get(i),reg1);//把每个参数先存到t0中
            if(i<8){//存到a0-7中
                String reg_name="a"+i;
                curBlock.addIns(new ASMmv(new ASMRegister(reg_name),reg1));
            }else{//存到栈中
                int offset=curFunc.getArgs_offset(i);
                AddStore(reg1,new ASMAddr(sp,offset));
            }
        }
        curBlock.addIns(new ASMcall(it.FunctionName));

        if(!it.ResultType.equals("void")){
            int offset=curFunc.getVar_offset(it.result);
            ASMAddr addr=new ASMAddr(new ASMRegister("sp"),offset);
            AddStore(new ASMRegister("a0"),addr);
        }
        //再把a0-7还原
        for(int i=0;i<Math.min(8,it.ArgsVal.size());i++){
            String reg_name="a"+i;
            AddLoad(new ASMRegister(reg_name),new ASMAddr(sp,curFunc.getArgs_offset(i)));
        }
    }

    public void visit(Getelementptr it){
        var reg1=new ASMRegister("t0");//存指针
        var reg2=new ASMRegister("t1");//存第几个
        var reg3=new ASMRegister("t2");//存4
        var reg4=new ASMRegister("t3");//存偏移量
        var sp=new ASMRegister("sp");

        String index;
        loadReg(it.pointer,reg1);
        if(it.idx.size()==1){
            index=it.idx.get(0);
        }else{
            index=it.idx.get(1);
        }
        loadReg(index,reg2);
        curBlock.addIns(new ASMli(reg3,4));
        curBlock.addIns(new ASMarith("mul",reg2,reg3,reg4));
        curBlock.addIns(new ASMarith("add",reg1,reg4,reg2));
        //现在reg2里面存的是要找的地址
        int offset=curFunc.getVar_offset(it.result);
        AddStore(reg2,new ASMAddr(sp,offset));
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
        int offset=curFunc.getVar_offset(it.result);
        AddStore(reg3,new ASMAddr(new ASMRegister("sp"),offset));
    }

    public void visit(Load it){
        var reg1=new ASMRegister("t0");//用于临时存储
        var sp=new ASMRegister("sp");
        loadReg(it.pointer,reg1);
        int offset=curFunc.getVar_offset(it.result);
        AddStore(reg1,new ASMAddr(sp,offset));
    }

    //IR中没用到
    public void visit(Phi it){}

    public void visit(Ret it){
        if(!it.type.equals("void")){
            loadReg(it.value,new ASMRegister("a0"));
        }
        var ra=new ASMRegister("ra");//返回地址
        var sp=new ASMRegister("sp");//栈指针

        AddLoad(ra,new ASMAddr(sp,curFunc.stacksize-4));//lw ra,stacksize-4(sp)
        AddAddi(sp,sp, curFunc.stacksize);//addi sp,sp,stacksize
        curBlock.addIns(new ASMret());
    }

    public void visit(Select it){
        var reg1=new ASMRegister("t0");
        var reg2=new ASMRegister("t1");
        loadReg(it.cond,reg1);//把条件载入t0
        loadReg(it.val1,reg2);//先把第一个值载入t1
        String tmplabel="select."+program.select_cnt++;
        curBlock.addIns(new ASMbranch(reg1,tmplabel,"bnez"));//如果条件正确，则已经完成，则跳转到tmplabel块进行赋值
        loadReg(it.val2,reg2);//否则，把第二个值载入t1
        curBlock=curFunc.addBlock(new ASMBlock(tmplabel));

        int offset=curFunc.getVar_offset(it.result);
        AddStore(reg2,new ASMAddr(new ASMRegister("sp"),offset));
    }

    public void visit(Store it){
        var reg1=new ASMRegister("t0");
        var reg2=new ASMRegister("t1");
        var sp=new ASMRegister("sp");
        loadReg(it.value,reg1);
        if(it.pointer.charAt(0)=='@'){//全局变量
            curBlock.addIns(new ASMla(reg2,it.pointer));//从全局变量label获取地址
            curBlock.addIns(new ASMsw(reg1,new ASMAddr(reg2,0)));
        }else{//局部变量
            loadReg(it.pointer,reg2);
            curBlock.addIns(new ASMsw(reg1,new ASMAddr(reg2,0)));
        }
    }
}
