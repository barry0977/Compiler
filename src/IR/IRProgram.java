package IR;

import IR.module.IRClassDef;
import IR.module.IRFuncDecl;
import IR.module.IRFuncDef;
import IR.module.IRGlobalVarDef;

import java.util.ArrayList;

public class IRProgram extends IRNode{
    public ArrayList<IRGlobalVarDef> globalvars;
    public ArrayList<IRClassDef>classs;
    public ArrayList<IRFuncDef>funcs;
    public ArrayList<IRFuncDecl>builtinfuncs;
    public boolean haveinit=false;

    public IRProgram(){
        globalvars = new ArrayList<>();
        classs = new ArrayList<>();
        funcs = new ArrayList<>();
        builtinfuncs = new ArrayList<>();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(IRFuncDecl f:builtinfuncs){
            sb.append(f.toString());
        }
        for(IRClassDef c : classs){
            sb.append(c.toString());
        }
        for(IRGlobalVarDef gv : globalvars){
            sb.append(gv.toString());
        }
        for (IRFuncDef f : funcs){
            sb.append(f.toString());
        }
        return sb.toString();
    }

    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
