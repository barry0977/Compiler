package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class UnaryExprNode extends ExprNode {
    public ExprNode expr;
    public String opCode;

    public UnaryExprNode(Position pos){
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
