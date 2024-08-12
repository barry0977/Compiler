package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class PreExprNode extends ExprNode {
    public ExprNode expr;
    public String opCode;

    public PreExprNode(Position pos){
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
