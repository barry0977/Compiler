package AST.Expr;

import AST.ASTNode;
import Util.Position;

import java.lang.reflect.Type;

public abstract class ExprNode extends ASTNode {
    public Type type;
    public boolean isLeftValue;
    public ExprNode(Position pos) {
        super(pos);
    }
}
