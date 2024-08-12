package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class ConditionExprNode extends ExprNode {
    public ExprNode cond_, then_, else_;

    public ConditionExprNode (Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
