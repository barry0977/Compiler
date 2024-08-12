package AST.Expr;

import AST.ASTVisitor;
import AST.Type.Type;
import Util.Position;

public class MemberFuncExprNode extends ExprNode {
    public ExprNode obj;
    public String func;
    public Type functype;

    public MemberFuncExprNode(ExprNode obj, String func, Type functype, Position pos) {
        super(pos);
        this.obj = obj;
        this.func = func;
        this.functype = functype;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
