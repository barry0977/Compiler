package ASM.module;

import ASM.ASMBlock;

import java.util.ArrayList;
import java.util.HashMap;

/*
stack(arg1-arg8 : a0-a7)
arg1  -----sp-----
|
argn
var1
|
varn
ra
 */
public class ASMFuncDef {
    public String name;
    public int argscnt,varcnt=0;
    public int callArgsCnt=0;//call语句所包含的参数个数的最大值
    public ArrayList<ASMBlock> body;
    public HashMap<String,Integer>args_ord;//参数在参数列表中的顺序
    public HashMap<String,Integer>var_ord;//所有局部变量的顺序(从1开始)
    public int stacksize=0;

    public ASMFuncDef(String name,int argscnt){
        this.name=name;
        this.argscnt=argscnt;
        body = new ArrayList<>();
        args_ord = new HashMap<>();
        var_ord = new HashMap<>();
    }

    public ASMBlock addBlock(ASMBlock block){
        body.add(block);
        return block;
    }

    public int getVar_offset(String name){
        int ord=var_ord.get(name);
        return (callArgsCnt+ord)*4;
    }

    public int getArgs_offset(int i){
        return stacksize+4*i;//参数存在调用者的栈上
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
        return sb.toString();
    }
}
