package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class NewVarExprNode extends ExprNode {
    public NewVarExprNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}

