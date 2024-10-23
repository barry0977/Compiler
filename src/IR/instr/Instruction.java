package IR.instr;

import IR.IRNode;
import IR.IRVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

abstract public class Instruction extends IRNode {
    //for LiveAnalysis
    public ArrayList<Instruction> preds = new ArrayList<>();//记录所有指令的前驱，从而从出口倒序bfs遍历
    public ArrayList<Instruction> succs = new ArrayList<>();//记录所有指令的后继，用于计算out
    public HashSet<String>use = new HashSet<>();
    public HashSet<String>def = new HashSet<>();
    public HashSet<String>in = new HashSet<>();
    public HashSet<String>out = new HashSet<>();
    public Instruction() {}

    abstract public void rename(HashMap<String, String> map);

    //活跃分析 获得use def
    abstract public void getUseDef();

    public void addUse(String obj) {
        if(obj != null && obj.charAt(0)=='%'){//是局部变量(全局变量和常量不是use)
            use.add(obj);
        }
    }
    @Override
    abstract public String toString();

    @Override
    abstract public void accept(IRVisitor visitor);
}
