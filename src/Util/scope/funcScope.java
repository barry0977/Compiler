package Util.scope;

public class funcScope extends Scope{
    public funcScope(Scope parent){
        super(parent);
        this.stype=scopeType.funcscope;
    }
}
