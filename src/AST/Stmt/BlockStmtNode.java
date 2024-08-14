package AST.Stmt;

import AST.ASTVisitor;
import Util.Position;
import Util.scope.Scope;

import java.util.ArrayList;

public class BlockStmtNode extends StmtNode {
    public ArrayList<StmtNode> statements;
    public Scope scope;

    public BlockStmtNode(Position pos) {
        super(pos);
        statements = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
