package AST.Stmt;

import AST.Expr.ExprNode;
import Util.Position;
import AST.ASTVisitor;

public class IfStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode trueStmt,falseStmt;

    public IfStmtNode(ExprNode condition, StmtNode trueStmt, StmtNode falseStmt, Position pos) {
        super(pos);
        this.condition = condition;
        this.trueStmt = trueStmt;
        this.falseStmt = falseStmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
