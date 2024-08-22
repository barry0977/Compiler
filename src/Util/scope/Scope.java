package Util.scope;

import AST.Type.Type;
import AST.Type.exprType;
import Util.Decl.FuncDecl;
import Util.Position;
import Util.error.semanticError;

import java.util.HashMap;

public class Scope {
    public enum scopeType{//
        blockscope,classscope,funcscope,loopscope
    }
    public HashMap<String, Type> vars;//只存了变量
    public scopeType stype;//
    public Scope parent;
    public int depth=0;//在第几层scope
    public int order=0;//同一层中目前在第几个scope
    public int num=0;//用于记录当前scope有几个孩子
    public int cnt=0;//用于短路求值的块的命名

    public Scope(Scope parent) {
        this.parent = parent;
        this.vars = new HashMap<>();
        this.stype = scopeType.blockscope;
        this.depth=parent.depth+1;
        this.order=++parent.num;
    }

    public Scope getParent(){
        return parent;
    }

    public void setParent(Scope parent){
        this.parent = parent;
    }

    public void addVar(String name, Type type, Position pos) {
        if(vars.containsKey(name)){
            System.out.println("Multiple Definitions");
            throw new semanticError("Duplicate variable name: "+name,pos);
        }
        vars.put(name, type);
    }

    public boolean containsVar(String name,boolean lookUpon) {
        if(vars.containsKey(name)) {
            return true;
        }else if(parent!=null && lookUpon) {
            return parent.containsVar(name, lookUpon);
        }else{
            return false;
        }
    }

    public Type getVar(String name,boolean lookUpon) {
        if(vars.containsKey(name)) {
            return vars.get(name);
        }else if(parent!=null && lookUpon) {
            return parent.getVar(name, lookUpon);
        }else{
            return null;
        }
    }

    public FuncDecl getFunc(String name, boolean lookUpon) {
        if(this instanceof globalScope) {//全局
            if(((globalScope) this).funcDecls.containsKey(name)) {
                return ((globalScope) this).funcDecls.get(name);
            }else{
                return null;
            }
        }else if(this instanceof classScope) {//类
            if(((classScope) this).funcs.containsKey(name)){
                return (((classScope) this).funcs.get(name));
            }else if(parent!=null && lookUpon) {
                return parent.getFunc(name, lookUpon);
            }else{
                return null;
            }
        }else{//只能往上找
            if(parent!=null && lookUpon) {
                return ((parent).getFunc(name, lookUpon));
            }else{
                return null;
            }
        }
    }

    //寻找所在的第一个类
    public String inClass(){
        if(this instanceof classScope) {
            return ((classScope) this).classname;
        }else if(this.parent != null){
            return this.parent.inClass();
        }else{
            return null;
        }
    }

    public boolean inLoop(){
        if(this instanceof loopScope) {
            return true;
        }else if(this.parent != null){
            return this.parent.inLoop();
        }else{
            return false;
        }
    }

    public boolean inFunc(){
        if(this instanceof funcScope) {
            return true;
        }else if(this.parent != null){
            return this.parent.inFunc();
        }else{
            return false;
        }
    }

    public void setReturn(){
        if(this instanceof funcScope) {
            ((funcScope) this).isReturned=true;
        }else if(this.parent != null){
            this.parent.setReturn();
        }
    }

    public Type getReturnType(){
        if(this instanceof funcScope) {
            return ((funcScope) this).returnType;
        }else if(this.parent != null){
            return this.parent.getReturnType();
        }else{
            return null;
        }
    }

    //寻找Identifier（变量或函数）
    public exprType getIdentifier(String name){
        if(vars.containsKey(name)) {
            return new exprType(vars.get(name));
        }else{
            if(parent!=null){
                return parent.getIdentifier(name);
            }
        }
        return null;
    }
}