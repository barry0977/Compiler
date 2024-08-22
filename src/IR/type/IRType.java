package IR.type;

import AST.Type.Type;

public class IRType {
    public String typename;

    public IRType(Type type) {
        if (type.isVoid){
            typename = "void";
        }else if(type.isInt){
            typename = "i32";
        }else if(type.isBool){
            typename = "i1";
        }else{
            typename = "ptr";
        }
    }

    public String toString() {
        return typename;
    }
}
