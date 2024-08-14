package AST.Type;

import Parser.MxParser;

public class Type {
    public boolean isInt = false, isBool = false, isString = false, isClass = false,isVoid = false,isNull = false;
    public String typeName;//类的名字
    public int dim=0;//维数

    public Type(String type, int dim) {
        if(type.equals("int")){
            this.isInt = true;
        }else if(type.equals("bool")){
            this.isBool = true;
        }else if(type.equals("string")){
            this.isString=true;
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

    public Type(Type t){
        this.isInt = t.isInt;
        this.isBool = t.isBool;
        this.isString = t.isString;
        this.isClass = t.isClass;
        this.isVoid=t.isVoid;
        this.isNull=t.isNull;
        this.typeName = t.typeName;
        this.dim = t.dim;
    }

    public Type(MxParser.TypeContext ctx){
        String type=ctx.typename().getText();
        int dim=ctx.LeftBracket().size();

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
        this.dim=dim;
    }

    //数组和类可与常量null进行比较
    public boolean equals(Type t){
        if(t.isNull){
            return isNull||isClass||dim>0;
        }
        if(isNull){
            return t.isNull||t.isClass||t.dim>0;
        }

        if(this.dim==t.dim){
            if(this.isClass&&t.isClass){
                return this.typeName==t.typeName;
            }else if(!this.isClass&&!t.isClass){
                return this.isVoid==t.isVoid&&this.isBool==t.isBool&&this.isString==t.isString&&this.isInt==t.isInt;
            }
        }
        return false;
    }
}
