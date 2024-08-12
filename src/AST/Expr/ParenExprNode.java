package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class ParenExprNode extends ExprNode {
    public ExprNode expr;

    public ParenExprNode(ExprNode expr,Position pos) {
        super(pos);
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
