package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class AssignExprNode extends ExprNode {
    public ExprNode lhs,rhs;

    public AssignExprNode(ExprNode lhs, ExprNode rhs, Position pos) {
        super(pos);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
