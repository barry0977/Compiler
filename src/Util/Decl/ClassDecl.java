package Util.Decl;

import AST.Def.ClassDefNode;
import AST.Type.Type;

import java.util.HashMap;

public class ClassDecl {
    public String name;
    public HashMap<String, Type>vars;
    public HashMap<String, FuncDecl> funcs;

    public ClassDecl(String name){
        this.name = name;
        this.vars = new HashMap<>();
        this.funcs = new HashMap<>();
    }

    public ClassDecl(ClassDefNode classdef){
        this.name=classdef.name;
        this.vars = new HashMap<>();
        this.funcs = new HashMap<>();
        for(var varDef : classdef.vars){
            for(var x:varDef.vars){//一个变量声明语句可能有多个变量
                this.vars.put(x.first,varDef.vartype);
            }
        }
        for(var funcDef : classdef.funcs){
            this.funcs.put(funcDef.funcname,new FuncDecl(funcDef));
        }
    }
}
