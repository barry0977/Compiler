package Util.scope;

import AST.Type.Type;
import Util.Decl.ClassDecl;
import Util.Decl.FuncDecl;

import java.util.HashMap;

public class globalScope extends Scope{
    public HashMap<String, ClassDecl>classDecls;
    public HashMap<String, FuncDecl>funcDecls;

    public globalScope(){
        classDecls = new HashMap<>();
        funcDecls = new HashMap<>();
        funcDecls.put("print",new FuncDecl("print",new Type("void",0),"string",true));
        funcDecls.put("println",new FuncDecl("println",new Type("void",0),"string",true) );
        funcDecls.put("printInt",new FuncDecl("printInt",new Type("void",0),"int",true));
        funcDecls.put("printlnInt",new FuncDecl("printlnInt",new Type("void",0),"int",true));
        funcDecls.put("getString",new FuncDecl("getString",new Type("string",0),"",false));
        funcDecls.put("getInt",new FuncDecl("getInt",new Type("int",0),"",false));
        funcDecls.put("toString",new FuncDecl("toString",new Type("string",0),"int",true));

    }
}
