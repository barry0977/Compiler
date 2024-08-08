package AST.Expr;

import AST.ASTNode;
import Util.Position;

import java.lang.reflect.Type;

public abstract class ExprNode extends ASTNode {
    Type type;
    public ExprNode(Position pos) {
        super(pos);
    }
}
