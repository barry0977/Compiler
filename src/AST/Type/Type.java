package AST.Type;

public class Type {
    public boolean isInt = false, isBool = false, isString = false, isClass = false,isVoid = false,isNull = false;
    public String typeName;//类的名字
    public int dim;//维数

    Type(String type, int dim) {
        if(type.equals("int")){
            this.isInt = true;
        }else if(type.equals("bool")){
            this.isBool = true;
        }else if(type.equals("string")){
            this.isString = true;
        }else if(type.equals("void")){
            this.isVoid = true;
        }else if(type.equals("null")){
            this.isNull = true;
        }else{
            this.isClass = true;
            this.typeName = type;
        }

        this.dim = dim;
    }


}
