package AST.Expr;

import AST.ASTVisitor;
import AST.Expr.BasicExpr.ArrayConstNode;
import Util.Position;
import java.util.ArrayList;

public class NewArrayExprNode extends ExprNode{
    public ArrayList<ExprNode> sizelist;
    public ArrayConstNode init;

    public NewArrayExprNode(Position pos){
        super(pos);
        sizelist = new ArrayList<>();
        init = new ArrayConstNode(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
