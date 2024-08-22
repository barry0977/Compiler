package IR.module;

import IR.type.IRType;

public class IRGlobalVarDef {
    public String name;
    public IRType type;
    public String value=null;

    public IRGlobalVarDef() {}

    public String toString(){
        String res= "@"+name+" = global "+type.toString();
        if(value==null){
            res+=" 0\n";
        }else{
            res+=" "+value+"\n";
        }
        return res;
    }
}
