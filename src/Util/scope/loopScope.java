package Util.scope;

public class loopScope extends Scope{
    public boolean isWhile=false;
    public boolean isFor=false;
    public int ord;

    public loopScope(Scope parent){
        super(parent);
        this.stype=scopeType.loopscope;
    }
}
