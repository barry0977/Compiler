package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class ConditionExprNode extends ExprNode {
    public ExprNode cond_, then_, else_;

    public ConditionExprNode (ExprNode cond, ExprNode then, ExprNode else_, Position pos) {
        super(pos);
        this.cond_ = cond;
        this.then_ = then;
        this.else_ = else_;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
