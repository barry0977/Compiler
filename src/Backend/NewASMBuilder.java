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
import Util.Pair;
import jdk.dynalink.beans.StaticClass;

import java.util.*;


public class NewASMBuilder implements IRVisitor {
    public ASMProgram program;
    public ASMBlock curBlock;
    public ASMFuncDef curFunc;
    public IRBlock curIRBlock;

    public IRFuncDef curIRFunc;//for phiElimination


    public NewASMBuilder(ASMProgram _program) {
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
            curBlock.addIns(new ASMarith("add",addr.reg,new ASMRegister("t6"),new ASMRegister("t6")));
            curBlock.addIns(new ASMlw(rd,new ASMAddr(new ASMRegister("t6"),0)));
        }
    }

    public void AddStore(ASMRegister rd, ASMAddr addr){
        int offset=addr.offset;
        if(offset<=2047&&offset>=-2048){
            curBlock.addIns(new ASMsw(rd,addr));
        }else{
            curBlock.addIns(new ASMli(new ASMRegister("t6"),offset));
            curBlock.addIns(new ASMarith("add",addr.reg,new ASMRegister("t6"),new ASMRegister("t6")));
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
    public void loadReg(String obj,ASMRegister rd){
        if(obj==null){
            curBlock.addIns(new ASMli(rd,0));
            return;
        }
        if(obj.charAt(0)=='@'){//全局变量
            curBlock.addIns(new ASMla(rd,obj));
        }else if(obj.charAt(0)=='%'){//局部变量
            ASMRegister sp=new ASMRegister("sp");
            var id=curFunc.args_ord.get(obj);
            if(id==null){//局部变量
                if(curFunc.var_on_reg.containsKey(obj)){//已经分配了寄存器，则直接返回该寄存器
                    int index=curFunc.var_on_reg.get(obj);
                    if(curFunc.istocall&&index<12&&curFunc.call_offset.containsKey(index)){//已经被存到栈上(s和不在out中的寄存器没有存上去)
                        AddLoad(rd,new ASMAddr(sp,curFunc.call_offset.get(index)));
                    }else{//在寄存器中，直接把目标寄存器改成该寄存器
                        rd.name=curFunc.getRegister(index).name;
                    }
                }else{//否则,从栈上读取
                    int offset=curFunc.getVar_offset(obj);
                    AddLoad(rd,new ASMAddr(sp,offset));
                }
            }else{//参数
                if(id<8){//存在寄存器中
                    if(curFunc.istocall){//已经被存到栈上
                        int index=curFunc.var_on_reg.get(obj);
                        AddLoad(rd,new ASMAddr(sp,curFunc.call_offset.get(index)));
                    }else{
                        rd.name="a"+id;
                    }
                }else{//存在stack中
                    int offset=curFunc.getArgs_offset(id)+curFunc.stacksize;
                    AddLoad(rd,new ASMAddr(sp,offset));
                }
            }
        }else{//常量
            curBlock.addIns(new ASMli(rd,Integer.parseInt(obj)));
        }
    }

    public void StoreReg(String result,ASMRegister rd){
        if(curFunc.var_on_reg.containsKey(result)){//存到寄存器中
            ASMRegister res=curFunc.getRegister(curFunc.var_on_reg.get(result));
            curBlock.addIns(new ASMmv(res,rd));
        }else if(curFunc.var_ord.containsKey(result)){//存在栈上
            int offset=curFunc.var_ord.get(result);
            AddStore(rd,new ASMAddr(new ASMRegister("sp"),offset));
        }else{
            System.err.println("Store fail: from "+rd.toString()+" to "+result);
        }
    }

    //储存Call指令的参数
    public void StoreArgs(Call it){
        ASMRegister sp=new ASMRegister("sp");
        for(int i=0;i<it.ArgsVal.size();i++){
            String value=it.ArgsVal.get(i);
            if(i<8){//存到a0-7中
                ASMRegister rd=new ASMRegister("a"+i);
                if(value==null){
                    curBlock.addIns(new ASMli(rd,0));
                }else if(value.charAt(0)=='@'){//全局变量
                    curBlock.addIns(new ASMla(rd,value));
                }else if(value.charAt(0)=='%'){//局部变量
                    var id=curFunc.args_ord.get(value);
                    if(id==null){//局部变量
                        if(curFunc.var_on_reg.containsKey(value)){//分配了寄存器(a/t/s)
                            int index=curFunc.var_on_reg.get(value);
                            if(index>i){//没被修改，可直接mv
                                curBlock.addIns(new ASMmv(rd,curFunc.getRegister(curFunc.var_on_reg.get(value))));
                            }else if(index<i){//被修改
                                if(curFunc.call_offset.containsKey(index)){//从栈上读取
                                    AddLoad(rd,new ASMAddr(sp,curFunc.call_offset.get(index)));
                                }else{//从S中读取
                                    var rs=curFunc.getRegister(curFunc.Reg2S.get(index));
                                    curBlock.addIns(new ASMmv(rd,rs));
                                }
                            }
                        }else{//在栈上
                            int offset=curFunc.getVar_offset(value);
                            AddLoad(rd,new ASMAddr(sp,offset));
                        }
                    }else{//参数
                        if(id<8){//存在寄存器中
                            if(i<id){//a_id寄存器还没被修改，可直接mv
                                curBlock.addIns(new ASMmv(rd,new ASMRegister("a"+id)));
                            }else if(i>id){//已经被修改，得从栈上读取
                                int index=curFunc.var_on_reg.get(value);
                                if(curFunc.call_offset.containsKey(index)){
                                    AddLoad(rd,new ASMAddr(sp,curFunc.call_offset.get(index)));
                                }else{
                                    var rs=curFunc.getRegister(curFunc.Reg2S.get(index));
                                    curBlock.addIns(new ASMmv(rd,rs));
                                }
                            }//i=id,不需要移动
                        }else{//存在stack中
                            int offset=curFunc.getArgs_offset(id)+curFunc.stacksize;
                            AddLoad(rd,new ASMAddr(sp,offset));
                        }
                    }
                }else{//常量
                    curBlock.addIns(new ASMli(rd,Integer.parseInt(value)));
                }
            }else{//存到栈中(只存要调用函数的参数，而不是原函数)
                var tmp=new ASMRegister("t5");
                int offset=curFunc.getArgs_offset(i);
                if(value==null){
                    curBlock.addIns(new ASMli(tmp,0));
                    AddStore(tmp,new ASMAddr(sp,offset));
                }else if(value.charAt(0)=='@'){//全局变量
                    curBlock.addIns(new ASMla(tmp,value));
                    AddStore(tmp,new ASMAddr(sp,offset));
                }else if(value.charAt(0)=='%'){//局部变量
                    var id=curFunc.args_ord.get(value);
                    if(id==null){//局部变量
                        if(curFunc.var_on_reg.containsKey(value)){//分配了寄存器(a/t/s)
                            int index=curFunc.var_on_reg.get(value);
                            if(curFunc.call_offset.containsKey(index)){//从栈上读取
                                AddLoad(tmp,new ASMAddr(sp,curFunc.call_offset.get(index)));
                                AddStore(tmp,new ASMAddr(sp,offset));
                            }else{//从S中读取
                                var rs=curFunc.getRegister(curFunc.Reg2S.get(index));
                                AddStore(rs,new ASMAddr(sp,offset));
                            }
                        }else{//在栈上
                            int offset1=curFunc.getVar_offset(value);
                            AddLoad(tmp,new ASMAddr(sp,offset1));
                            AddStore(tmp,new ASMAddr(sp,offset));
                        }

//                        if(curFunc.var_on_reg.containsKey(value)){//分配了寄存器(不会是a_)
//                            AddStore(curFunc.getRegister(curFunc.var_on_reg.get(value)),new ASMAddr(sp,offset));
//                        }else{//在栈上
//                            int offset1=curFunc.getVar_offset(value);
//                            AddLoad(tmp,new ASMAddr(sp,offset1));
//                            AddStore(tmp,new ASMAddr(sp,offset));
//                        }
                    }else{//参数
                        if(id<8){//存在寄存器中
                            int index=curFunc.var_on_reg.get(value);
                            if(curFunc.call_offset.containsKey(index)){
                                AddLoad(tmp,new ASMAddr(sp,curFunc.call_offset.get(index)));
                                AddStore(tmp,new ASMAddr(sp,offset));
                            }else{//在S寄存器中
                                var rs=curFunc.getRegister(curFunc.Reg2S.get(index));
                                AddStore(rs,new ASMAddr(sp,offset));
                            }
                        }else{//存在stack中
                            int offset1=curFunc.getArgs_offset(id)+curFunc.stacksize;
                            AddLoad(tmp,new ASMAddr(sp,offset1));
                            AddStore(tmp,new ASMAddr(sp,offset));
                        }
                    }
                }else{//常量
                    curBlock.addIns(new ASMli(tmp,Integer.parseInt(value)));
                    AddStore(tmp,new ASMAddr(sp,offset));
                }
            }
        }
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
        curIRBlock=it;
        for(var ins:it.statements){
            Iterator<String> iterator=ins.def.iterator();
            if(iterator.hasNext()){//有def，如果def没有被使用过，则不执行这条指令
                String result=iterator.next();
                if(curFunc.args_ord.containsKey(result)||curFunc.var_on_reg.containsKey(result)||curFunc.var_ord.containsKey(result)){
                    ins.accept(this);
                }else{
                    if(ins instanceof Call){//Call指令需要执行
                        ((Call) ins).result=null;
                        ins.accept(this);
                    }else{
                        continue;//跳过
                    }
                }
            }else{
                ins.accept(this);
            }
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


        curFunc.Regs_size=it.Regs_size;
        //检查所有call指令in的变量，将这些变量的寄存器存下来
        //恢复时只需恢复out中的变量
        //也要把call参数中大于8个的部分存下来
        int cnt=0;
        int s_free=12;//空余的s寄存器
        for(var instr:it.entry.statements){
            if(instr instanceof Call){
                cnt=0;
                s_free=12;
                curFunc.callArgsCnt=Math.max(curFunc.callArgsCnt,((Call) instr).ArgsVal.size());
                for(var variable:instr.in){
                    if(it.RegAlloc.containsKey(variable)){//不是call指令的def，并且保存在寄存器中
                        if(it.RegAlloc.get(variable)<12){//s寄存器由callee保存，只需保存a和t
                            cnt++;
                        }else{
                            s_free--;
                        }
                    }
                }
                curFunc.reg_to_save_caller=Math.max(cnt-s_free,curFunc.reg_to_save_caller);
                curFunc.Regs_size=Math.max(curFunc.Regs_size,12-Math.max(s_free-cnt,0));
            }
        }
        for(var block:it.body){
            for(var instr:block.statements){
                if(instr instanceof Call){
                    cnt=0;
                    s_free=12;
                    curFunc.callArgsCnt=Math.max(curFunc.callArgsCnt,((Call) instr).ArgsVal.size());
                    for(var variable:instr.in){
                        if(it.RegAlloc.containsKey(variable)){//不是call指令的def，并且保存在寄存器中
                            if(it.RegAlloc.get(variable)<12){//s寄存器由callee保存，只需保存a和t
                                cnt++;
                            }else{
                                s_free--;
                            }
                        }
                    }
                    curFunc.reg_to_save_caller=Math.max(cnt-s_free,curFunc.reg_to_save_caller);
                    curFunc.Regs_size=Math.max(curFunc.Regs_size,12-Math.max(s_free-cnt,0));
                }
            }
        }


        //为所有溢出在栈上的变量分配位置
        for(var localvar:it.SpilledVar){
            curFunc.var_ord.put(localvar,curFunc.varcnt++);
        }
        curFunc.var_on_reg=it.RegAlloc;//分配了寄存器的变量

        curFunc.stacksize+=it.stacksize;//溢出的变量在栈上的空间
        curFunc.stacksize+=4*curFunc.reg_to_save_caller;//由caller保存的寄存器
        curFunc.stacksize+=4*curFunc.Regs_size;//由callee保存的使用的s寄存器

        curFunc.stacksize+=4*Math.max(0,curFunc.callArgsCnt-8);//调用函数时，被调用函数多余的参数

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

        //callee save
        //把会占用的s寄存器存在栈上
        for(int s =0;s<curFunc.Regs_size;s++){
            var sR=new ASMRegister("s"+s);
            AddStore(sR,new ASMAddr(sp,4*Math.max(0,curFunc.callArgsCnt-8)+s*4));
        }

        it.entry.accept(this);
        for(var block:it.body){
            block.accept(this);
        }
        PhiElimination(it);
    }

    public void visit(IRGlobalVarDef it){
        program.globalvars.add(new ASMGlobalVarDef(it.name,it.value));
    }

    public void visit(IRStringDef it){
        program.strs.add(new ASMStringDef(".str."+it.label,it.origin,it.length+1));
    }

    public void visit(Alloca it){}

    public void visit(Binary it){
        curBlock.addIns(new ASMcomment(it.toString()));
        var reg1=new ASMRegister("t5");
        var reg2=new ASMRegister("t6");

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

        if(curFunc.var_on_reg.containsKey(it.result)){//存在寄存器上
            curBlock.addIns(new ASMarith(op,reg1,reg2,curFunc.getRegister(curFunc.var_on_reg.get(it.result))));
        }else{//存在栈中
            curBlock.addIns(new ASMarith(op,reg1,reg2,new ASMRegister("t5")));
            int offset=curFunc.getVar_offset(it.result);
            AddStore(new ASMRegister("t5"),new ASMAddr(new ASMRegister("sp"),offset));
        }
    }


    //br的立即数范围较小，最好用jump指令
    public void visit(Br it){
        curBlock.addIns(new ASMcomment(it.toString()));
        //此时一定是一个IRBlock的teriminalStatement
        curIRBlock.asmBlock=curBlock;
        curIRBlock.line_order=curBlock.body.size();

        if(it.haveCondition){
            var reg1=new ASMRegister("t5");
            String truelabel=curFunc.name+"."+it.iftrue;
            String falselabel=curFunc.name+"."+it.iffalse;
            String tmplabel= "br."+program.br_cnt++;
            loadReg(it.cond,reg1);
            curBlock.addIns(new ASMbranch(reg1,tmplabel,"beqz"));//如果错，就先跳到tmplabel
            curBlock.addIns(new ASMj(truelabel));//如果对，直接跳到truelabel
            curBlock=curFunc.addBlock(new ASMBlock(tmplabel));
            curBlock.addIns(new ASMj(falselabel));//tmplabel后直接跳到falselabel
        }else{
            curBlock.addIns(new ASMj(curFunc.name+"."+it.dest));
        }
    }

    //调用者保存a和t寄存器，调用完再还原
    public void visit(Call it){
        curBlock.addIns(new ASMcomment(it.toString()));
        var reg1=new ASMRegister("t5");//用于临时存储参数
        var sp=new ASMRegister("sp");
        //先把会占用的a,t存起来(存的位置只负责原函数的a,t)
        curFunc.call_offset=new HashMap<>();
        curFunc.istocall = true;
        int initpos=4*Math.max(0,curFunc.callArgsCnt-8)+4*curFunc.Regs_size;
        BitSet free_S=new BitSet(12);//1表示空闲，0表示被占用
        free_S.set(0,12);
        for(var Reg:it.in){
            if(curFunc.var_on_reg.containsKey(Reg)){
                int regno=curFunc.var_on_reg.get(Reg);
                if(regno>=12){
                    free_S.clear(regno-12);
                }
            }
        }
        for(var Reg:it.in){
            if(curFunc.var_on_reg.containsKey(Reg)){//call指令的in，并且保存在寄存器中
                int regno=curFunc.var_on_reg.get(Reg);
                ASMRegister toSave;
                if(regno<8){//a_
                    toSave=new ASMRegister("a"+regno);
                }else if(regno<12){//t_
                    toSave=new ASMRegister("t"+(regno-8));
                }else{//s_
                    continue;
                }
                int first_free_s=free_S.nextSetBit(0);
                if(first_free_s==-1){//没有空余S寄存器，存到栈上
                    curFunc.call_offset.put(regno,initpos);
                    AddStore(toSave,new ASMAddr(sp,initpos));
                    initpos+=4;
                }else{//存到空余S寄存器上
                    curFunc.Reg2S.put(regno,first_free_s+12);
                    var rds=curFunc.getRegister(first_free_s+12);
                    curBlock.addIns(new ASMmv(rds,toSave));
                    free_S.clear(first_free_s);
                }
            }
        }

        StoreArgs(it);

        curBlock.addIns(new ASMcall(it.FunctionName));

        if(!it.ResultType.equals("void")&&it.result!=null){//先把结果存到t5中
            curBlock.addIns(new ASMmv(new ASMRegister("t5"),new ASMRegister("a0")));
        }

        //再把a,t还原
        for(var Reg:it.out){
            if((!Reg.equals(it.result))&&curFunc.var_on_reg.containsKey(Reg)){//只需还原call指令的out，并且不是call指令的def，且保存在寄存器中
                int regno=curFunc.var_on_reg.get(Reg);
                ASMRegister toSave;
                if(regno<8){//a_
                    toSave=new ASMRegister("a"+regno);
                }else if(regno<12){//t_
                    toSave=new ASMRegister("t"+(regno-8));
                }else{//s_
                    continue;
                }
                if(curFunc.call_offset.containsKey(regno)){//存在栈上
                    AddLoad(toSave,new ASMAddr(sp,curFunc.call_offset.get(regno)));
                }else{//存在S寄存器中
                    var rds=curFunc.getRegister(curFunc.Reg2S.get(regno));
                    curBlock.addIns(new ASMmv(toSave,rds));
                }
            }
        }
        curFunc.istocall=false;

        if(!it.ResultType.equals("void")&&it.result!=null){
            if(curFunc.var_on_reg.containsKey(it.result)){//存在寄存器上
                ASMRegister rd=curFunc.getRegister(curFunc.var_on_reg.get(it.result));
                curBlock.addIns(new ASMmv(rd,new ASMRegister("t5")));
            }else{//存在栈中
                int offset=curFunc.getVar_offset(it.result);
                AddStore(new ASMRegister("t5"),new ASMAddr(new ASMRegister("sp"),offset));
            }
        }
    }

    public void visit(Getelementptr it){
        curBlock.addIns(new ASMcomment(it.toString()));
        var reg1=new ASMRegister("t4");//存指针
        var reg2=new ASMRegister("t5");//存第几个
        var sp=new ASMRegister("sp");

        String index;
        loadReg(it.pointer,reg1);
        if(it.idx.size()==1){
            index=it.idx.get(0);
        }else{
            index=it.idx.get(1);
        }
        loadReg(index,reg2);
        curBlock.addIns(new ASMarithimm("slli",reg2,new ASMRegister("t5"),2));//t5 =reg2 << 2
        reg2=new ASMRegister("t5");
        curBlock.addIns(new ASMarith("add",reg1,reg2,reg2));//reg2=reg1+reg2
        //现在reg2里面存的是要找的地址
        if(curFunc.var_on_reg.containsKey(it.result)){//存在寄存器上
            ASMRegister rd=curFunc.getRegister(curFunc.var_on_reg.get(it.result));
            curBlock.addIns(new ASMmv(rd,reg2));
        }else{//存在栈中
            int offset=curFunc.getVar_offset(it.result);
            AddStore(reg2,new ASMAddr(sp,offset));
        }
    }

    public void visit(Icmp it){
        curBlock.addIns(new ASMcomment(it.toString()));
        //用t4，t5保存两个操作数
        var reg1=new ASMRegister("t4");
        var reg2=new ASMRegister("t5");
        var sp=new ASMRegister("sp");

        loadReg(it.op1,reg1);
        loadReg(it.op2,reg2);
        curBlock.addIns(new ASMarith("sub",reg1,reg2,new ASMRegister("t5")));
        reg1=new ASMRegister("t4");
        reg2=new ASMRegister("t5");
        var op=it.cond;
        if(op.equals("eq")){//==
            curBlock.addIns(new ASMset(reg2,reg2,"seqz"));
        }else if(op.equals("ne")){//!=
            curBlock.addIns(new ASMset(reg2,reg2,"snez"));
        }else if(op.equals("sgt")){//>
            curBlock.addIns(new ASMset(reg2,reg2,"sgtz"));
        }else if(op.equals("sge")){//>=
            //reg1>reg2 || reg1==reg2
            curBlock.addIns(new ASMset(reg1,reg2,"sgtz"));
            curBlock.addIns(new ASMset(reg2,reg2,"seqz"));
            curBlock.addIns(new ASMarith("or",reg1,reg2,reg2));
        }else if(op.equals("slt")){//<
            curBlock.addIns(new ASMset(reg2,reg2,"sltz"));
        }else if(op.equals("sle")){//<=
            //reg1<reg2 || reg1==reg2
            curBlock.addIns(new ASMset(reg1,reg2,"sltz"));
            curBlock.addIns(new ASMset(reg2,reg2,"seqz"));
            curBlock.addIns(new ASMarith("or",reg1,reg2,reg2));

        }
        if(curFunc.var_on_reg.containsKey(it.result)){//存在寄存器上
            ASMRegister rd=curFunc.getRegister(curFunc.var_on_reg.get(it.result));
            curBlock.addIns(new ASMmv(rd,reg2));
        }else{//存在栈中
            int offset=curFunc.getVar_offset(it.result);
            AddStore(reg2,new ASMAddr(sp,offset));
        }
    }

    public void visit(Load it){
        //load的result一定是局部变量，且不会是alloca出来的
        curBlock.addIns(new ASMcomment(it.toString()));
        var reg1=new ASMRegister("t5");//用于临时存储
        var sp=new ASMRegister("sp");
        if(it.pointer.charAt(0)=='@'){//全局变量
            curBlock.addIns(new ASMla(reg1, it.pointer));//存pointer指向的地址
            if(curFunc.var_on_reg.containsKey(it.result)){//存在寄存器上
                ASMRegister rd=curFunc.getRegister(curFunc.var_on_reg.get(it.result));
                curBlock.addIns(new ASMlw(rd,new ASMAddr(reg1,0)));//存pointer指向的值
            }else{//存在栈中
                curBlock.addIns(new ASMlw(reg1,new ASMAddr(reg1,0)));//存pointer指向的值
                int offset1=curFunc.getVar_offset(it.result);
                AddStore(reg1,new ASMAddr(sp,offset1));
            }
        }else{//局部变量
            loadReg(it.pointer,reg1);//reg1中存pointer指向的地址
            if(curFunc.var_on_reg.containsKey(it.result)){//存在寄存器上
                ASMRegister rd=curFunc.getRegister(curFunc.var_on_reg.get(it.result));
                curBlock.addIns(new ASMlw(rd,new ASMAddr(reg1,0)));//存pointer指向的值
            }else{//存在栈上
                curBlock.addIns(new ASMlw(reg1,new ASMAddr(reg1,0)));//存pointer指向地址的值
                int offset1=curFunc.getVar_offset(it.result);
                AddStore(reg1,new ASMAddr(sp,offset1));
            }
        }
    }

    //IR中没用到
    public void visit(Phi it){}

    public void visit(Ret it){
        curBlock.addIns(new ASMcomment(it.toString()));
        var ra=new ASMRegister("ra");//返回地址
        var sp=new ASMRegister("sp");//栈指针

        //此时一定是一个IRBlock的teriminalStatement
        curIRBlock.asmBlock=curBlock;
        curIRBlock.line_order=curBlock.body.size();

        ASMRegister a0=new ASMRegister("a0");
        if(!it.type.equals("void")){
            if(it.value==null){
                curBlock.addIns(new ASMli(a0,0));
                return;
            }
            if(it.value.charAt(0)=='@'){//全局变量
                curBlock.addIns(new ASMla(a0,it.value));
            }else if(it.value.charAt(0)=='%'){//局部变量
                var id=curFunc.args_ord.get(it.value);
                if(id==null){//局部变量
                    if(curFunc.var_on_reg.containsKey(it.value)){//已经分配了寄存器，则直接返回该寄存器
                        int index=curFunc.var_on_reg.get(it.value);
                        curBlock.addIns(new ASMmv(a0,curFunc.getRegister(index)));
                    }else{//否则,从栈上读取
                        int offset=curFunc.getVar_offset(it.value);
                        AddLoad(a0,new ASMAddr(sp,offset));
                    }
                }else{//参数
                    if(id<8){//存在寄存器中
                        curBlock.addIns(new ASMmv(a0,new ASMRegister("a"+id)));
                    }else{//存在stack中
                        int offset=curFunc.getArgs_offset(id)+curFunc.stacksize;
                        ASMAddr addr=new ASMAddr(sp,offset);
                        AddLoad(a0,addr);
                    }
                }
            }else{//常量
                curBlock.addIns(new ASMli(a0,Integer.parseInt(it.value)));
            }
        }

        //把s寄存器还原
        for(int s =0;s<curFunc.Regs_size;s++){
            var sR=new ASMRegister("s"+s);
            AddLoad(sR,new ASMAddr(sp,4*Math.max(0,curFunc.callArgsCnt-8)+s*4));
        }

        AddLoad(ra,new ASMAddr(sp,curFunc.stacksize-4));//lw ra,stacksize-4(sp)
        AddAddi(sp,sp, curFunc.stacksize);//addi sp,sp,stacksize
        curBlock.addIns(new ASMret());
    }

    public void LoadForSelect(String obj,ASMRegister rd){
        if(obj==null){
            curBlock.addIns(new ASMli(rd,0));
            return;
        }
        if(obj.charAt(0)=='@'){//全局变量
            curBlock.addIns(new ASMla(rd,obj));
        }else if(obj.charAt(0)=='%'){//局部变量
            ASMRegister sp=new ASMRegister("sp");
            var id=curFunc.args_ord.get(obj);
            if(id==null){//局部变量
                if(curFunc.var_on_reg.containsKey(obj)){//已经分配了寄存器，mv
                    int index=curFunc.var_on_reg.get(obj);
                    curBlock.addIns(new ASMmv(rd,curFunc.getRegister(index)));
                }else{//否则,从栈上读取
                    int offset=curFunc.getVar_offset(obj);
                    AddLoad(rd,new ASMAddr(sp,offset));
                }
            }else{//参数
                if(id<8){//存在寄存器中
                    curBlock.addIns(new ASMmv(rd,new ASMRegister("A"+id)));
                }else{//存在stack中
                    int offset=curFunc.getArgs_offset(id)+curFunc.stacksize;
                    AddLoad(rd,new ASMAddr(sp,offset));
                }
            }
        }else{//常量
            curBlock.addIns(new ASMli(rd,Integer.parseInt(obj)));
        }
    }

    public void visit(Select it){
        curBlock.addIns(new ASMcomment(it.toString()));
        var reg1=new ASMRegister("t5");
        var reg2=new ASMRegister("t4");
        loadReg(it.cond,reg1);//把条件载入t5
        LoadForSelect(it.val1,reg2);//先把第一个值载入t4
        String tmplabel="select."+program.select_cnt++;
        curBlock.addIns(new ASMbranch(reg1,tmplabel,"bnez"));//如果条件正确，则已经完成，则跳转到tmplabel块进行赋值
        LoadForSelect(it.val2,reg2);//否则，把第二个值载入t4
        curBlock=curFunc.addBlock(new ASMBlock(tmplabel));

        if(curFunc.var_on_reg.containsKey(it.result)){//存在寄存器上
            ASMRegister rd=curFunc.getRegister(curFunc.var_on_reg.get(it.result));
            curBlock.addIns(new ASMmv(rd,reg2));
        }else{//存在栈上
            int offset=curFunc.getVar_offset(it.result);
            AddStore(reg2,new ASMAddr(new ASMRegister("sp"),offset));
        }
    }

    public void visit(Store it){
        //store的pointer不会是参数，只会是局部变量和全局变量
        curBlock.addIns(new ASMcomment(it.toString()));
        var reg1=new ASMRegister("t5");//value
        var reg2=new ASMRegister("t6");//ptr
        var sp=new ASMRegister("sp");
        loadReg(it.value,reg1);//reg1里面有要存的值
        if(it.pointer.charAt(0)=='@'){//全局变量
            curBlock.addIns(new ASMla(reg2,it.pointer));//从全局变量label获取全局变量地址
            curBlock.addIns(new ASMsw(reg1,new ASMAddr(reg2,0)));
        }else{//局部变量
            if(curFunc.var_on_reg.containsKey(it.pointer)){//存在寄存器上
                ASMRegister rd=curFunc.getRegister(curFunc.var_on_reg.get(it.pointer));
                AddStore(reg1,new ASMAddr(rd,0));
            }else{//存在栈上
                int offset=curFunc.getVar_offset(it.pointer);//存的地址
                AddLoad(reg2,new ASMAddr(sp,offset));//指向的位置
                AddStore(reg1,new ASMAddr(reg2,0));
            }
        }
    }

    //消除phi
    public void PhiElimination(IRFuncDef func){
        curIRFunc=func;
        func.setMap();
        for(var block:func.body){
            VisitBlockPhi(block);
        }
        HandleMoveOrder(func.entry);
        for(var block:func.body){
            HandleMoveOrder(block);
        }
    }

    //把phi指令全都改成mv
    public void VisitBlockPhi(IRBlock block){
        for(var phiIns:block.philist.values()){
            String result=phiIns.result;
            for(int i=0;i<phiIns.vals.size();i++){
                String value=phiIns.vals.get(i);
                IRBlock src=curIRFunc.blockmap.get(phiIns.labels.get(i));
                if(value==null||value.equals("null")){
                    continue;
                }
                src.moves.add(new Pair<>(result,value));//存进对应的前驱块
            }
        }
    }

    public void AddStoreForPhi(ASMRegister rd,ASMAddr addr,IRBlock irBlock){
        int offset=addr.offset;
        ASMBlock asmBlock=irBlock.asmBlock;
        int location;
        if(offset<=2047&&offset>=-2048){
            location=irBlock.line_order++;
            asmBlock.body.add(location,new ASMsw(rd,addr));
        }else{
            location=irBlock.line_order++;
            asmBlock.body.add(location,new ASMli(new ASMRegister("t6"),offset));
            location=irBlock.line_order++;
            asmBlock.body.add(location,new ASMarith("add",addr.reg,new ASMRegister("t6"),new ASMRegister("t6")));
            location=irBlock.line_order++;
            asmBlock.body.add(location,new ASMsw(rd,new ASMAddr(new ASMRegister("t6"),0)));
        }
    }

    public void AddLoadForPhi(ASMRegister rd,ASMAddr addr,IRBlock irBlock){
        int offset=addr.offset;
        ASMBlock asmBlock=irBlock.asmBlock;
        int location;
        if(offset<=2047&&offset>=-2048){
            location=irBlock.line_order++;
            asmBlock.body.add(location,new ASMlw(rd,addr));
        }else{
            location=irBlock.line_order++;
            asmBlock.body.add(location,new ASMli(new ASMRegister("t6"),offset));
            location=irBlock.line_order++;
            asmBlock.body.add(location,new ASMarith("add",addr.reg,new ASMRegister("t6"),new ASMRegister("t6")));
            location=irBlock.line_order++;
            asmBlock.body.add(location,new ASMlw(rd,new ASMAddr(new ASMRegister("t6"),0)));
        }
    }

    //翻译move指令
    public void AddMoveFromPhi(String dest,String value,IRBlock block){
        ASMBlock asmBlock=block.asmBlock;
        int location;
        ASMRegister sp=new ASMRegister("sp");
        if(curFunc.var_on_reg.containsKey(dest)){//存在寄存器
            var rd=curFunc.getRegister(curFunc.var_on_reg.get(dest));
            if(value==null){
                location=block.line_order++;
                asmBlock.body.add(location,new ASMli(rd,0));
            }else if(value.charAt(0)=='@'){
                location=block.line_order++;
                asmBlock.body.add(location,new ASMla(rd,value));
            }else if(value.charAt(0)=='%'){
                if(curFunc.var_on_reg.containsKey(value)){
                    var rs=curFunc.getRegister(curFunc.var_on_reg.get(value));
                    if(!rs.name.equals(rd.name)){
                        location=block.line_order++;
                        asmBlock.body.add(location,new ASMmv(rd,rs));
                    }
                }else{
                    if(curFunc.args_ord.containsKey(value)){//参数
                        int id=curFunc.args_ord.get(value);
                        int offset=curFunc.getArgs_offset(id)+curFunc.stacksize;
                        AddLoadForPhi(rd,new ASMAddr(sp,offset),block);
                    }else{
                        int offset=curFunc.getVar_offset(value);
                        AddLoadForPhi(rd,new ASMAddr(sp,offset),block);
                    }
                }
            }else{
                location=block.line_order++;
                asmBlock.body.add(location,new ASMli(rd,Integer.parseInt(value)));
            }
        }else{//存在栈上
            int offset=curFunc.getVar_offset(dest);
            ASMRegister tmp=new ASMRegister("t5");
            if(value==null){
                location=block.line_order++;
                asmBlock.body.add(location,new ASMli(tmp,0));
                AddStoreForPhi(tmp,new ASMAddr(sp,offset),block);
            }else if(value.charAt(0)=='@'){
                location=block.line_order++;
                asmBlock.body.add(location,new ASMla(tmp,value));
                AddStoreForPhi(tmp,new ASMAddr(sp,offset),block);
            }else if(value.charAt(0)=='%'){
                if(curFunc.var_on_reg.containsKey(value)){
                    var rs=curFunc.getRegister(curFunc.var_on_reg.get(value));
                    AddStoreForPhi(rs,new ASMAddr(sp,offset),block);
                }else{
                    if(curFunc.args_ord.containsKey(value)){//参数
                        int id=curFunc.args_ord.get(value);
                        int offset1=curFunc.getArgs_offset(id)+curFunc.stacksize;
                        AddLoadForPhi(tmp,new ASMAddr(sp,offset1),block);
                        AddStoreForPhi(tmp,new ASMAddr(sp,offset),block);
                    }else{
                        int offset1=curFunc.getVar_offset(value);
                        AddLoadForPhi(tmp,new ASMAddr(sp,offset1),block);
                        AddStoreForPhi(tmp,new ASMAddr(sp,offset),block);
                    }
                }
            }else{
                location=block.line_order++;
                asmBlock.body.add(location,new ASMli(tmp,Integer.parseInt(value)));
                AddStoreForPhi(tmp,new ASMAddr(sp,offset),block);
            }
        }
    }

    //翻译move指令
    public void AddMoveDirect(int dest,int value,IRBlock block){
        ASMBlock asmBlock=block.asmBlock;
        int location;
        ASMRegister sp=new ASMRegister("sp");
        if(dest<0){//dest为寄存器
            var rd=curFunc.getRegister(-(dest+1));
            if(value<0){//value为寄存器
                var rs=curFunc.getRegister(-(value+1));
                if(!rs.name.equals(rd.name)){
                    location=block.line_order++;
                    asmBlock.body.add(location,new ASMmv(rd,rs));
                }
            }else{//value为栈
                AddLoadForPhi(rd,new ASMAddr(sp,value),block);
            }
        }else{//dest为栈
            if(value<0){//value为寄存器
                var rs=curFunc.getRegister(-(value+1));
                AddStoreForPhi(rs,new ASMAddr(sp,dest),block);
            }else{//value为栈
                ASMRegister tmp=new ASMRegister("t5");
                AddLoadForPhi(tmp,new ASMAddr(sp,value),block);
                AddStoreForPhi(tmp,new ASMAddr(sp,dest),block);
            }
        }
    }

    //是否是常数或者全局指针
    public boolean isConst(String obj){
        if(obj==null){
            return true;
        }else if(obj.charAt(0)=='@'){
            return true;
        }else if(obj.charAt(0)=='%'){
            return false;
        }else{
            return true;
        }
    }

    public int getKey(String obj){
        int key;
        if(curFunc.var_on_reg.containsKey(obj)){//存在寄存器中
            key=-curFunc.var_on_reg.get(obj)-1;//可能是0
        }else{//存在栈上
            if(curFunc.args_ord.containsKey(obj)){
                int id=curFunc.args_ord.get(obj);
                key=curFunc.getArgs_offset(id)+curFunc.stacksize;
            }else{
                key=curFunc.getVar_offset(obj);
            }
        }
        return key;
    }


    //通过基环外向树（所有点入度为1）确定执行的先后顺序
    public void HandleMoveOrder(IRBlock block){
        if(block.moves.isEmpty()){
            return;
        }
        ASMRegister sp=new ASMRegister("sp");
        ArrayList<Pair<String,String>>ImmDef=new ArrayList<>();//存立即数，最后翻译
        HashMap<Integer, HashSet<Integer>>succs=new HashMap<>();
        HashMap<Integer,Integer>preds=new HashMap<>();
        HashMap<Integer,Integer>visited=new HashMap<>();
        for(int i=0;i<block.moves.size();i++){
            var dest=block.moves.get(i).first;
            var value=block.moves.get(i).second;
            if(isConst(value)){//直接加入一个列表，最后翻译，不需要重排序
                ImmDef.add(new Pair<>(dest,value));
            }else{
                int destkey=getKey(dest);
                int srckey=getKey(value);
                if(succs.containsKey(srckey)){
                    succs.get(srckey).add(destkey);
                }else{
                    succs.put(srckey,new HashSet<>());
                    succs.get(srckey).add(destkey);
                }
                preds.put(destkey,srckey);
                visited.put(srckey,0);
                visited.put(destkey,0);
            }
        }

        int size=preds.size();
        for(var node:preds.keySet()){
            if(visited.get(node)==1){//已经访问过
                continue;
            }
            int cur=node;
            boolean isRing=true;//是否有环
            int root=0;//如果是链，则有根节点
            while(visited.get(cur)!=1){
                visited.put(cur,1);
                if(preds.containsKey(cur)){//有前驱
                    cur=preds.get(cur);
                }else{//已经到头
                    isRing=false;
                    root=cur;
                    break;
                }
            }
            if(isRing){//环
                HashSet<Integer>ring=new HashSet<>();
                ring.add(cur);
                int begin=preds.get(cur);
                while(begin!=cur){
                    ring.add(begin);
                    begin=preds.get(begin);
                }
                //先对环上的每个点作为根节点，dfs
                for(var ori:ring){
                    visited.put(ori,1);
                    dfs2(ori,ring,succs,preds,block,visited);
                }
                //先用临时寄存器把cur存起来
                var tmpReg=new ASMRegister("t4");
                if(cur<0){
                    int line=block.line_order++;
                    var curReg=curFunc.getRegister(-(cur+1));
                    ASMBlock asmBlock=block.asmBlock;
                    asmBlock.body.add(line,new ASMmv(tmpReg,curReg));
                }else{
                    AddLoadForPhi(tmpReg,new ASMAddr(sp,cur),block);
                }
                //绕着环赋值一遍
                int now=cur;
                while(preds.get(now)!=cur){
                    AddMoveDirect(now,preds.get(now),block);
                    now=preds.get(now);
                }
                //从临时寄存器中取出存到now中
                if(now<0){
                    int line=block.line_order++;
                    var nowReg=curFunc.getRegister(-(now+1));
                    ASMBlock asmBlock=block.asmBlock;
                    asmBlock.body.add(line,new ASMmv(nowReg,tmpReg));
                }else{
                    AddStoreForPhi(tmpReg,new ASMAddr(sp,now),block);
                }
            }else{//链
                //直接dfs执行
                visited.put(root,1);
                dfs1(root,succs,preds,block,visited);
            }
        }
        //再进行常数赋值
        for(var Imm:ImmDef){
            AddMoveFromPhi(Imm.first,Imm.second,block);
        }
    }

    //dfs一棵树
    public void dfs1(int root,HashMap<Integer,HashSet<Integer>>succs,HashMap<Integer,Integer>preds,IRBlock block,HashMap<Integer,Integer>visited){
        if(succs.containsKey(root)){
            for(var child:succs.get(root)){
                visited.put(child,1);
                dfs1(child,succs,preds,block,visited);
            }
        }
        if(preds.containsKey(root)){//dfs后序遍历
            AddMoveDirect(root,preds.get(root),block);
        }
    }

    public void dfs2(int root,HashSet<Integer>Ring,HashMap<Integer,HashSet<Integer>>succs,HashMap<Integer,Integer>preds,IRBlock block,HashMap<Integer,Integer>visited){
        if(succs.containsKey(root)){
            for(var child:succs.get(root)){
                if(!Ring.contains(child)){
                    visited.put(child,1);
                    dfs1(child,succs,preds,block,visited);
                }
            }
        }
        if(preds.containsKey(root)&&!Ring.contains(root)){//不在环上
            AddMoveDirect(root,preds.get(root),block);
        }
    }
}