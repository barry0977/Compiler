package AST.Expr;

import AST.ASTVisitor;
import AST.Expr.BasicExpr.ArrayConstNode;
import Util.Position;
import java.util.ArrayList;

public class NewArrayExprNode extends ExprNode{
    public ArrayList<Integer> sizelist;
    public ArrayConstNode init;

    public NewArrayExprNode(ArrayConstNode arr,Position pos){
        super(pos);
        sizelist = new ArrayList<>();
        init = arr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
