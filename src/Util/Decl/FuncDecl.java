package Util.Decl;

import AST.Def.FuncDefNode;
import AST.Type.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class FuncDecl {
    public String name;
    public Type returnType;
    public ArrayList<Type> params;


    public FuncDecl(String name, Type returnType,String type1,String type2,boolean have) {
        this.name = name;
        this.returnType = returnType;
        this.params = new ArrayList<>();
        if (have) {
            this.params.add(new Type("type1",0));
            this.params.add(new Type("type2",0));
        }
    }

    public FuncDecl(FuncDefNode funcdef){
        this.name=funcdef.name;
        this.returnType=funcdef.returntype;
        this.params=new ArrayList<>();
        for(var x:funcdef.paraslist.Paralist){
            this.params.add(x.first);
        }
    }
}
