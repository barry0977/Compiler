package AST.Expr.BasicExpr;

import AST.ASTVisitor;
import AST.Expr.ExprNode;
import Util.Position;

import java.util.ArrayList;

public class ArrayConstNode extends ExprNode {
    public ArrayList<ArrayConstNode> elements;

    public ArrayConstNode(Position pos) {
        super(pos);
        elements = new ArrayList<>();

    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
