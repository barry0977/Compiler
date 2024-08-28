package ASM.module;

import ASM.ASMBlock;

import java.util.ArrayList;
import java.util.HashMap;

public class ASMFuncDef {
    public String name;
    public int argscnt;
    public ArrayList<ASMBlock> body;
    public HashMap<String,Integer>args_ord;
    public HashMap<String,Integer>args_addr;
    public HashMap<String,Integer>var_sp_offset;
    public int stacksize=0;

    public ASMFuncDef(String name,int argscnt){
        this.name=name;
        this.argscnt=argscnt;
        body = new ArrayList<>();
        args_ord = new HashMap<>();
        args_addr = new HashMap<>();
    }

    public ASMBlock addBlock(ASMBlock block){
        body.add(block);
        return block;
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        return sb.toString();
    }
}
