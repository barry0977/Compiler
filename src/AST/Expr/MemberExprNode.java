package AST.Expr;

import AST.ASTVisitor;
import AST.Type.Type;
import Util.Position;

public class MemberExprNode extends ExprNode {
    public ExprNode obj;
    public String member;
    public Type membertype;

    public MemberExprNode(ExprNode obj, String member, Type membertype, Position pos) {
        super(pos);
        this.obj = obj;
        this.member = member;
        this.membertype = membertype;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
