package Util.scope;

import AST.Def.FuncDefNode;
import AST.Type.Type;
import Util.Decl.FuncDecl;
import Util.Position;
import Util.error.semanticError;
import Util.error.syntaxError;

import java.util.HashMap;

public class classScope extends Scope {
    public HashMap<String, FuncDecl>funcs;

    public classScope(Scope parent) {
        super(parent);
        this.stype=scopeType.classscope;
    }

    public void addFunc(FuncDefNode func){
        if(funcs.containsKey(func.name)){
            throw new semanticError("Duplicate function name: "+func.name,func.pos);
        }
        if(vars.containsKey(func.name)){
            throw new semanticError("Duplicate function name with variable: "+func.name,func.pos);
        }
        funcs.put(func.name,new FuncDecl(func));
    }

    @Override
    public void addVar(String name, Type type, Position pos) {
        if(vars.containsKey(name)){
            throw new semanticError("Duplicate variable name: "+name,pos);
        }
        if(funcs.containsKey(name)){
            throw new semanticError("Duplicate viriable name with function: "+name,pos);
        }
        vars.put(name, type);
    }

}
