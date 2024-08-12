package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class SufExprNode extends ExprNode {
    public enum opType{
        addi,subi
    }

    public opType opCode;
    public ExprNode expr;

    public SufExprNode(opType opCode, ExprNode expr, Position pos) {
        super(pos);
        this.opCode = opCode;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
