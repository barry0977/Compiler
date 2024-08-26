package IR.type;

import AST.Type.Type;

public class IRType {
    public String typename;

    public IRType(Type type) {
        if(type.dim==0){
            if (type.isVoid){
                typename = "void";
                return;
            }else if(type.isInt){
                typename = "i32";
                return;
            }else if(type.isBool){
                typename = "i1";
                return;
            }
        }
        typename="ptr";
    }

    public IRType(String typename) {
        this.typename = typename;
    }

    public String toString() {
        return typename;
    }
}
