package AST.Expr.BasicExpr;

import AST.ASTVisitor;
import AST.Type.Type;
import Util.Position;

public class IntConstNode extends BasicExprNode {
    public int value;

    public IntConstNode(Position pos){
        super(pos);
        type=new Type("int",0);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}

