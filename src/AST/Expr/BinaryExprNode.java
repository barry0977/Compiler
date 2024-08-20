package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class BinaryExprNode extends ExprNode {
    public ExprNode lhs,rhs;
    public String opCode;

    public BinaryExprNode(Position pos){
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
