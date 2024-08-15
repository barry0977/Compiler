package AST.Expr;

import AST.ASTNode;
import AST.ASTVisitor;
import Util.Position;
import AST.Type.*;

public abstract class ExprNode extends ASTNode {
    public exprType type;
    public boolean isLeftValue;//是否是左值



    public ExprNode(Position pos) {
        super(pos);
    }

    public ExprNode(exprType type,Boolean is,Position pos) {
        super(pos);
        this.type=type;
        this.isLeftValue=is;
    }

    @Override
    abstract public void accept(ASTVisitor visitor);
}
