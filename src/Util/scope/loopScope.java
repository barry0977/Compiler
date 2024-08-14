package Util.scope;

public class loopScope extends Scope{
    public loopScope(Scope parent){
        super(parent);
        this.stype=scopeType.loopscope;
    }
}
