package Util.scope;

import AST.Type.Type;
import Util.Position;
import Util.error.semanticError;

import java.util.HashMap;

public class Scope {
    public enum scopeType{//
        blockscope,classscope,funcscope,loopscope
    }
    public HashMap<String, Type> vars;//只存了变量
    public scopeType stype;//
    private Scope parent;

    public Scope(Scope parent) {
        this.parent = parent;
        stype = scopeType.blockscope;
    }

    public Scope getParent(){
        return parent;
    }

    public void setParent(Scope parent){
        this.parent = parent;
    }

    public void addVar(String name, Type type, Position pos) {
        if(vars.containsKey(name)){
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
}
