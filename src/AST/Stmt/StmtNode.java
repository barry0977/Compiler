package AST.Stmt;

import AST.ASTNode;
import AST.ASTVisitor;
import Util.Position;
public abstract class StmtNode extends ASTNode {
    public StmtNode(Position pos) {
        super(pos);
    }

    @Override
    abstract public void accept(ASTVisitor visitor);
}
