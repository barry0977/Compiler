package Util.scope;

import AST.Type.Type;
import AST.Type.exprType;

import java.util.HashMap;

public class funcScope extends Scope{
    public Type returnType;
    public HashMap<String, Type> params;
    public boolean isReturned=false;

    public funcScope(Scope parent){
        super(parent);
        this.stype=scopeType.funcscope;
        params = new HashMap<>();
    }

    public funcScope(Scope parent, Type type){
        super(parent);
        this.stype=scopeType.funcscope;
        this.returnType = type;
        params = new HashMap<>();
    }

}
