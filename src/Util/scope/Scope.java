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
    public HashMap<String, Integer>varindex;//记录每个成员是类里面第几个成员
    public int index=0;
    public scopeType stype;//
    public Scope parent;
    public int depth=0;//在第几层scope
    public int order=0;//同一层中目前在第几个scope
    public int num=0;//用于记录当前scope有几个孩子
    public int cnt=0;//用于短路求值的块的命名
    public String looplabel;

    public Scope(Scope parent_) {
        this.parent = parent_;
        this.vars = new HashMap<>();
        this.varindex = new HashMap<>();
        this.stype = scopeType.blockscope;
        if(parent_!=null){
            this.depth=parent_.depth+1;
            this.order=++parent_.num;
        }else{
            this.depth=0;
            this.order=0;
        }
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
        varindex.put(name,index++);
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

    public loopScope getLoopScope(){
        if(this instanceof loopScope) {
            return (loopScope) this;
        }else if(this.parent != null){
            return this.parent.getLoopScope();
        }else{
            return null;
        }
    }

    public funcScope getFuncScope(){
        if(this instanceof funcScope) {
            return (funcScope) this;
        }else if(this.parent != null){
            return this.parent.getFuncScope();
        }else{
            return null;
        }
    }

    public classScope getClassScope(){
        if(this instanceof classScope) {
            return (classScope) this;
        }else if(this.parent != null){
            return this.parent.getClassScope();
        }else{
            return null;
        }
    }

    //找到第一次定义变量的scope
    public Scope findVarScope(String name){
        if(vars.containsKey(name)) {
            return this;
        }else if(parent != null){
            return parent.findVarScope(name);
        }else{
            return null;
        }
    }

    //找到第一次定义函数的scope
    public Scope findFuncScope(String name){
        if(this instanceof classScope) {
            if(((classScope) this).funcs.containsKey(name)) {
                return this;
            }
        }else if(this instanceof globalScope) {
            if(((globalScope) this).funcDecls.containsKey(name)) {
                return this;
            }
        }
        if(parent != null){
            return parent.findFuncScope(name);
        }
        return null;
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