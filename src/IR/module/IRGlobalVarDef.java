package IR.module;

import IR.IRNode;
import IR.IRVisitor;
import IR.type.IRType;

public class IRGlobalVarDef extends IRNode {
    public String name;
    public IRType type;
    public String value=null;

    public IRGlobalVarDef() {}

    public IRGlobalVarDef(String name, IRType type,String value) {
        this.name = name;
        this.type = type;
        this.value = value;
        if(value.equals("null")){
            if(type.typename.equals("ptr")){
                this.value=value;
            }
            else{
                this.value="0";
            }
        }
    }

    @Override
    public String toString(){
        String res= "@"+name+" = global "+type.toString();
        if(value==null){
            res+=" 0\n";
        }else{
            res+=" "+value+"\n";
        }
        return res;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
