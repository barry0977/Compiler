package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class SufExprNode extends ExprNode {
    public ExprNode expr;
    public String opCode;

    public SufExprNode(Position pos) {
        super(pos);
        this.opCode = opCode;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
