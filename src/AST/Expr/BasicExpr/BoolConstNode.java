package AST.Expr.BasicExpr;

import AST.ASTVisitor;
import AST.Type.Type;
import Util.Position;

public class BoolConstNode extends BasicExprNode{
    public Boolean boolValue;

    public BoolConstNode(Position pos){
        super(pos);
        type=new Type("bool",0);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
