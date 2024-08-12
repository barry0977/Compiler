package AST.Expr;

import Util.Position;

import java.util.ArrayList;

public class FuncExprNode extends ExprNode{
    public ExprNode func;
    public ArrayList<ExprNode> args;

    public FuncExprNode(Position pos) {
        super(pos);
        this.args = new ArrayList<>();
    }
}
