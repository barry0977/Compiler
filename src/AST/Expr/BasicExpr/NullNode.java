package AST.Expr.BasicExpr;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Type.Type;
import Util.Position;

public class NullNode extends BasicExprNode {
    public NullNode(Position pos) {
        super(pos);
        type=new Type("null",0);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
