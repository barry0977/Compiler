package Util.Decl;

import AST.Def.ClassDefNode;
import AST.Type.Type;
import Util.scope.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassDecl {
    public String name;
    public HashMap<String, Type>vars;
    public HashMap<String, Integer>varindex;//变量在类中的顺序
    public HashMap<String, FuncDecl> funcs;
    public boolean haveConstructor=false;
    public int size;//变量的个数

    public ClassDecl(String name){
        this.name = name;
        this.vars = new HashMap<>();
        this.funcs = new HashMap<>();
        this.varindex = new HashMap<>();
    }

    public ClassDecl(ClassDefNode classdef){
        int cnt = 0;
        this.name=classdef.name;
        this.vars = new HashMap<>();
        this.funcs = new HashMap<>();
        this.varindex = new HashMap<>();
        for(var varDef : classdef.vars){
            for(var x:varDef.vars){//一个变量声明语句可能有多个变量
                this.vars.put(x.first,varDef.vartype);
                this.varindex.put(x.first,cnt++);
            }
        }
        for(var funcDef : classdef.funcs){
            this.funcs.put(funcDef.name,new FuncDecl(funcDef));
        }
        this.size=classdef.vars.size();
        if(classdef.construct!=null){
            this.haveConstructor=true;
        }
    }

    public int getIndex(String name){
        return this.varindex.get(name);
    }

    public void setIndex(Scope cs){
        Set<Map.Entry<String,Integer>> entrySet=cs.varindex.entrySet();
        for(Map.Entry<String,Integer> entry:entrySet){
            String key = entry.getKey();
            Integer index = entry.getValue();
            this.varindex.put(key,index);
        }
    }
}
