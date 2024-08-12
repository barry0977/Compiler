package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class ArrayExprNode extends ExprNode{
    public ExprNode array;
    public ExprNode index;

    public ArrayExprNode(Position pos){
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
