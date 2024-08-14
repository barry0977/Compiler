package Util.scope;

import AST.Def.ClassDefNode;
import AST.Def.FuncDefNode;
import AST.Type.Type;
import Util.Decl.ClassDecl;
import Util.Decl.FuncDecl;
import Util.Position;
import Util.error.semanticError;

import java.util.HashMap;

public class globalScope extends Scope{
    public HashMap<String, ClassDecl>classDecls;
    public HashMap<String, FuncDecl>funcDecls;

    public globalScope(Scope parent){
        super(null);
        classDecls = new HashMap<>();
        funcDecls = new HashMap<>();
        funcDecls.put("print",new FuncDecl("print",new Type("void",0),"string","",true));
        funcDecls.put("println",new FuncDecl("println",new Type("void",0),"string","",true) );
        funcDecls.put("printInt",new FuncDecl("printInt",new Type("void",0),"int","",true));
        funcDecls.put("printlnInt",new FuncDecl("printlnInt",new Type("void",0),"int","",true));
        funcDecls.put("getString",new FuncDecl("getString",new Type("string",0),"","",false));
        funcDecls.put("getInt",new FuncDecl("getInt",new Type("int",0),"","",false));
        funcDecls.put("toString",new FuncDecl("toString",new Type("string",0),"int","",true));
        ClassDecl classDecl=new ClassDecl("string");
        classDecl.funcs.put("length",new FuncDecl("length",new Type("int",0),"","",false));
        classDecl.funcs.put("substring",new FuncDecl("substring",new Type("string",0),"int","int",true));
        classDecl.funcs.put("parseInt",new FuncDecl("parseInt",new Type("int",0),"","",false));
        classDecl.funcs.put("ord",new FuncDecl("ord",new Type("int",0),"int","",true));
        classDecls.put("string",classDecl);
        this.parent=null;
    }

    public FuncDecl getFunc(String name){
       if(funcDecls.containsKey(name)){
           return funcDecls.get(name);
       }else{
           return null;
       }
    }

    public ClassDecl getClass(String name){
        if(classDecls.containsKey(name)){
            return classDecls.get(name);
        }else{
            return null;
        }
    }

    public Type getVar(String name){
        if(vars.containsKey(name)){
            return vars.get(name);
        }else{
            return null;
        }
    }

    @Override
    public void addVar(String name, Type type, Position pos) {
        if(vars.containsKey(name)){
            throw new semanticError("Duplicate variable name: "+name,pos);
        }
        if(funcDecls.containsKey(name)){
            throw new semanticError("Duplicate variable name with function: "+name,pos);
        }
        vars.put(name, type);
    }

    public void addFunc(FuncDefNode func){
        if(funcDecls.containsKey(func.name)){
            throw new semanticError("Duplicate function name: "+func.name,func.pos);
        }
        if(vars.containsKey(func.name)){
            throw new semanticError("Duplicate function name with function: "+func.name,func.pos);
        }
        funcDecls.put(func.name,new FuncDecl(func));
    }

    public void addClass(ClassDefNode clas){
        if(classDecls.containsKey(clas.name)){
            throw new semanticError("Duplicate class name: "+clas.name,clas.pos);
        }
        classDecls.put(clas.name,new ClassDecl(clas));
    }
}
