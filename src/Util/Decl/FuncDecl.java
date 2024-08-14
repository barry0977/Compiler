package Util.Decl;

import AST.Def.FuncDefNode;
import AST.Type.Type;
import java.util.HashMap;

public class FuncDecl {
    public String name;
    public Type returnType;
    public HashMap<String, Type> params;


    public FuncDecl(String name, Type returnType,String type1,String type2,boolean have) {
        this.name = name;
        this.returnType = returnType;
        this.params = new HashMap<>();
        if (have) {
            this.params.put("",new Type("type1",0));
            this.params.put("",new Type("type2",0));
        }
    }

    public FuncDecl(FuncDefNode funcdef){
        this.name=funcdef.name;
        this.returnType=funcdef.returntype;
        this.params=new HashMap<>();
        for(var x:funcdef.paraslist.Paralist){
            this.params.put(x.second,x.first);
        }
    }
}
