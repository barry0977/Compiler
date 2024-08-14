package Util.Decl;

import AST.Def.FuncDefNode;
import AST.Def.ParalistNode;
import AST.Type.Type;

import java.util.ArrayList;

public class FuncDecl {
    public String name;
    public Type returnType;
    public ArrayList<Type> paramTypes;

    public FuncDecl(String name, Type returnType,String type,boolean have) {
        this.name = name;
        this.returnType = returnType;
        this.paramTypes = new ArrayList<>();
        if (have) {
            this.paramTypes.add(new Type(type,0));
        }
    }

    public FuncDecl(FuncDefNode funcdef){
        this.name=funcdef.funcname;
        this.returnType=funcdef.returntype;
        this.paramTypes=new ArrayList<>();
        for(var x:funcdef.paraslist.Paralist){
            this.paramTypes.add(x.first);
        }
    }


}
