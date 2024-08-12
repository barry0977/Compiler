package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class UnaryExprNode extends ExprNode {
    public enum unaryOpType{
        not,tilde,minus
    }
    public unaryOpType opCode;
    public ExprNode expr;

    public UnaryExprNode(unaryOpType opCode, ExprNode expr, Position pos){
        super(pos);
        this.opCode = opCode;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}