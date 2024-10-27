package IR.module;

import IR.IRBlock;
import IR.IRNode;
import IR.IRVisitor;
import IR.instr.Alloca;
import IR.type.IRType;
import Util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IRFuncDef extends IRNode {
    public String name;
    public IRType returntype;//返回类型
    public ArrayList<IRType> paramtypes;//参数类型
    public ArrayList<String> paramnames;//参数名
    public IRBlock entry;
    public ArrayList<IRBlock> body;
    public int cnt=0;//用于一个函数中的匿名变量的命名
    public int Icnt=0;//用于newarray中手写循环的变量i的命名
    public int shortname=0;//用于块编号
    public int blankcnt=0;//用于给critical edge分配块编号

    public int stacksize=0;
    public HashMap<String,Integer>RegAlloc;//分配了寄存器的变量
    public HashSet<String>SpilledVar;//溢出到栈上的变量
    public int Regs_size=0;//使用的s寄存器数量

    public HashMap<String,IRBlock>blockmap;//建立label到块的映射

    public IRFuncDef(){
        entry=new IRBlock("entry");
        body=new ArrayList<>();
        paramtypes=new ArrayList<>();
        paramnames=new ArrayList<>();
        RegAlloc=new HashMap<>();
        SpilledVar=new HashSet<>();
        blockmap=new HashMap<>();
    }

    public IRFuncDef(String name,String type){
        entry=new IRBlock("entry");
        this.name=name;
        this.returntype=new IRType(type);
        body=new ArrayList<>();
        paramtypes=new ArrayList<>();
        paramnames=new ArrayList<>();
        RegAlloc=new HashMap<>();
        SpilledVar=new HashSet<>();
        blockmap=new HashMap<>();
    }

    public IRBlock addBlock(IRBlock block){
        body.add(block);
        return block;
    }

    //函数内所有Alloca出来的变量(name+type)
    public ArrayList<Pair<String,String>>getAlloca(){
        ArrayList<Pair<String,String>> res=new ArrayList<>();
        for(var instr:entry.statements){
            if(instr instanceof Alloca){
                res.add(new Pair<>(((Alloca) instr).result,((Alloca) instr).type));
            }
        }
        if(entry.terminalStmt instanceof Alloca){
            res.add(new Pair<>(((Alloca) entry.terminalStmt).result,((Alloca) entry.terminalStmt).type));
        }
        return res;
    }

    public void setMap(){
        blockmap.put(entry.label,entry);
        for(var block:body){
            blockmap.put(block.label,block);
        }
    }

    @Override
    public String toString(){
        StringBuilder str=new StringBuilder();
        str.append("define ");
        str.append(returntype.toString());
        str.append(" @");
        str.append(name);
        str.append("(");
        for(int i=0; i<paramtypes.size(); i++){
            if(i>0){
                str.append(", ");
            }
            str.append(paramtypes.get(i).toString());
            str.append(" ");
            str.append(paramnames.get(i));
        }
        str.append(") {\n");
        str.append(entry.toString());
        for(int i=0; i<body.size(); i++){
            str.append(body.get(i).toString());
        }
        str.append("}\n\n");
        return str.toString();
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
