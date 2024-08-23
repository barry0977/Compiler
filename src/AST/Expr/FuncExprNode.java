package AST.Expr;

import AST.ASTVisitor;
import Util.Decl.FuncDecl;
import Util.Position;

import java.util.ArrayList;

public class FuncExprNode extends ExprNode{
    public ExprNode func;
    public ArrayList<ExprNode> args;
    public boolean isClass=false;//标记是否是成员函数

    public FuncExprNode(Position pos) {
        super(pos);
        this.args = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
