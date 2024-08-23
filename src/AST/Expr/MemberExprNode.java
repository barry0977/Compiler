package AST.Expr;

import AST.ASTVisitor;
import AST.Type.Type;
import Util.Position;

public class MemberExprNode extends ExprNode {
    public ExprNode obj;
    public String member;
    public Type membertype;
    public String classname;//类的名字

    public MemberExprNode( Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
