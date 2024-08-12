package AST.Expr;

import AST.ASTVisitor;
import AST.Type.Type;
import Util.Position;

public class MemberFuncExprNode extends ExprNode {
    public ExprNode obj;
    public String func;
    public Type functype;

    public MemberFuncExprNode(Position pos) {
        super(pos);

    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
