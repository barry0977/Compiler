package AST.Expr.BasicExpr;

import AST.ASTVisitor;
import Util.Position;

public class ThisNode extends BasicExprNode {
    public ThisNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
