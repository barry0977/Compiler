package AST.Expr.BasicExpr;

import AST.ASTVisitor;
import Util.Position;

public class IdentifierNode extends BasicExprNode{
    public String name;

    public IdentifierNode(Position pos){
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
