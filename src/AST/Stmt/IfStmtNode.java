package AST.Stmt;

import AST.Expr.ExprNode;
import Util.Position;
import AST.ASTVisitor;

public class IfStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode trueStmt,falseStmt;

    public IfStmtNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
