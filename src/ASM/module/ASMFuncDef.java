package ASM.module;

import ASM.ASMBlock;

import java.util.ArrayList;
import java.util.HashMap;

public class ASMFuncDef {
    public String name;
    public int argscnt,allocacnt=0,varcnt=0;
    public ArrayList<ASMBlock> body;
    public HashMap<String,Integer>args_ord;//参数在参数列表中的顺序
    public HashMap<String,Integer>alloca_ord;//所有alloca指令的顺序
    public HashMap<String,Integer>var_ord;//所有局部变量的顺序
    public int stacksize=0;

    public ASMFuncDef(String name,int argscnt){
        this.name=name;
        this.argscnt=argscnt;
        body = new ArrayList<>();
        args_ord = new HashMap<>();
        alloca_ord = new HashMap<>();
        var_ord = new HashMap<>();
    }

    public ASMBlock addBlock(ASMBlock block){
        body.add(block);
        return block;
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
