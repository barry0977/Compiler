package AST.Type;

import Parser.MxParser;
import Util.Decl.FuncDecl;

public class exprType extends Type {
    public boolean isFunc=false;
    public FuncDecl funcinfo;

    public exprType(Type type) {
        super(type);
    }

    public exprType(String name,int dim){
        super(name,dim);
    }

    public exprType(String name,FuncDecl funcinfo){
        super(name,0);
        this.funcinfo=funcinfo;
        this.isFunc=true;
        this.isClass=false;
    }

    public exprType(MxParser.TypeContext ctx){
        super(ctx);
    }
}
