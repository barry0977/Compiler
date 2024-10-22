package ASM.module;

import ASM.ASMBlock;

import java.util.ArrayList;
import java.util.HashMap;

/*
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
public class ASMFuncDef {
    public String name;
    public int argscnt,varcnt=0,alloccnt=0;
    public int callArgsCnt=0;//call语句所包含的参数个数的最大值
    public int stacksize_forcall=0;//用于在函数调用时处理a0-a7和函数多余参数存储的栈空间
    public ArrayList<ASMBlock> body;
    public HashMap<String,Integer>alloc_ord;//每次alloca标记一下(从0开始)
    public HashMap<String,Integer>args_ord;//参数在参数列表中的顺序(从0开始)
    public HashMap<String,Integer>var_ord;//所有局部变量的顺序(从0开始)
    public int stacksize=0;
    public boolean istocall = false;//在call一个函数时，自己的a0-a7会存到栈上，这时候如果被调用函数的参数在a0-a7中，要去栈上找

    public ASMFuncDef(String name,int argscnt){
        this.name=name;
        this.argscnt=argscnt;
        body = new ArrayList<>();
        args_ord = new HashMap<>();
        var_ord = new HashMap<>();
        alloc_ord = new HashMap<>();
    }

    public ASMBlock addBlock(ASMBlock block){
        body.add(block);
        return block;
    }

//    public int getAlloca_offset(String name){
//        int ord=(callArgsCnt+alloc_ord.get(name))*4;
//        return ord;
//    }
    public int getAlloca_offset(String name){
        return (stacksize_forcall+alloc_ord.get(name))*4;
    }

//    public int getVar_offset(String name){
//        int ord=var_ord.get(name);
//        return (callArgsCnt+alloccnt+ord)*4;
//    }
    public int getVar_offset(String name){
        int ord=var_ord.get(name);
        return (stacksize_forcall+alloccnt+ord)*4;
    }

    public int getArgs_offset(int i){
        return 4*(i-8);//参数存在调用者的栈上,如果要获取>8的参数，则还要加上当前函数的栈大小
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
