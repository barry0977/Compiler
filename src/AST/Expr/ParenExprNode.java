package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class ParenExprNode extends ExprNode {
    public ExprNode expr;

    public ParenExprNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
