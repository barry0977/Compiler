package Util.scope;

import AST.Type.Type;

import java.util.HashMap;

public class funcScope extends Scope{
    public Type returnType;
    public HashMap<String, Type> params;

    public funcScope(Scope parent){
        super(parent);
        this.stype=scopeType.funcscope;
    }
}
