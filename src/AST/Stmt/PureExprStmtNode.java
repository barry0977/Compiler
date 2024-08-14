package AST.Stmt;

import AST.ASTVisitor;
import AST.Expr.ExprNode;
import Util.Position;

public class PureExprStmtNode extends StmtNode {
    public ExprNode expr=null;

    public PureExprStmtNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
