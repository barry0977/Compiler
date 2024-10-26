package ASM.module;

import ASM.ASMBlock;
import ASM.util.ASMRegister;

import java.util.ArrayList;
import java.util.HashMap;

/*
原来
stack
arg1' (callee的多余参数）-----sp-----
|
argn'
arg1  (caller的a0-a7在call时存在栈上)
|
argn
alloc1
|
allocn
var1
|
varn
ra
 */


/*
现在
stack
arg1' (callee的多余参数）-----sp-----
|
argn'
s1
|
sn
ai1 (caller-save) 有reg_to_save_caller个
|
tin
var1
|
varn
ra
 */
public class ASMFuncDef {
    public String name;
    public int argscnt,varcnt=0,alloccnt=0;
    public ArrayList<ASMBlock> body;

    public int stacksize_forcall=0;//用于在函数调用时处理a0-a7和函数多余参数存储的栈空间
    public HashMap<String,Integer>alloc_ord;//每次alloca标记一下(从0开始)
    public boolean istocall = false;//在call一个函数时，自己的a0-a7会存到栈上，这时候如果被调用函数的参数在a0-a7中，要去栈上找

    //for NewASMBuilder
    public int callArgsCnt=0;//call语句所包含的参数个数的最大值
    public int reg_to_save_caller=0;//需要保存的a0-a7,t0-t3个数(caller-save)
    public int Regs_size=0;//使用的s寄存器数量
    public int stacksize=0;
    public HashMap<String,Integer>args_ord;//参数在参数列表中的顺序(从0开始)
    public HashMap<String,Integer>var_ord;//储存在栈上的局部变量的顺序(从0开始)
    public HashMap<String,Integer>var_on_reg;//在寄存器中的全局变量的编号
    public HashMap<Integer,Integer> call_offset=new HashMap<>();//在Call之前把a和t寄存器存到栈中的偏移量


    public ASMFuncDef(String name,int argscnt){
        this.name=name;
        this.argscnt=argscnt;
        body = new ArrayList<>();
        args_ord = new HashMap<>();
        var_ord = new HashMap<>();
        alloc_ord = new HashMap<>();
        var_on_reg = new HashMap<>();
    }

    public ASMBlock addBlock(ASMBlock block){
        body.add(block);
        return block;
    }

    public int getAlloca_offset(String name){
        return (stacksize_forcall+alloc_ord.get(name))*4;
    }

    public int getVar_offset(String name){
        int ord=var_ord.get(name);
        return (Math.max(0,callArgsCnt-8)+Regs_size+reg_to_save_caller+ord)*4;
    }

    public int getArgs_offset(int i){
        return 4*(i-8);//参数存在调用者的栈上,如果要获取>8的参数，则还要加上当前函数的栈大小
    }

    public ASMRegister getRegister(int index){
        if(index<8){
            return new ASMRegister("a"+index);
        }else if(index<12){
            return new ASMRegister("t"+(index-8));
        }else{
            return new ASMRegister("s"+(index-12));
        }
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("\t.globl ");
        sb.append(name);
        sb.append("\n");
        sb.append("\t.type ");
        sb.append(name);
        sb.append(",@function\n");
        for(var block:body){
            sb.append(block.toString());
        }
        sb.append("\n");
        return sb.toString();
    }
}
