package AST.Expr;

import Util.Position;

public class NewArrayExprNode extends ExprNode{
    public Arraylist<Integer> Size;

    public NewArrayExprNode(Position pos){
        super(pos);
    }

}
