package ASM.module;

import ASM.ASMBlock;

import java.util.ArrayList;
import java.util.HashMap;

/*
stack(arg1-arg8 : a0-a7)
arg1  -----sp-----
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
    public ArrayList<ASMBlock> body;
    public HashMap<String,Integer>alloc_ord;//每次alloca标记一下(从0开始)
    public HashMap<String,Integer>args_ord;//参数在参数列表中的顺序(从0开始)
    public HashMap<String,Integer>var_ord;//所有局部变量的顺序(从0开始)
    public int stacksize=0;

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

    public int getAlloca_offset(String name){
        int ord=(callArgsCnt+alloc_ord.get(name))*4;
        return ord;
    }

    public int getVar_offset(String name){
        int ord=var_ord.get(name);
        return (callArgsCnt+alloccnt+ord)*4;
    }

    public int getArgs_offset(int i){
        return 4*i;//参数存在调用者的栈上,如果要获取>8的参数，则还要加上当前函数的栈大小
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
