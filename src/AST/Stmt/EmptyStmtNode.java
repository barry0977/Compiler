package AST.Stmt;

import AST.ASTVisitor;
import Util.Position;

public class EmptyStmtNode extends StmtNode {
    public EmptyStmtNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
