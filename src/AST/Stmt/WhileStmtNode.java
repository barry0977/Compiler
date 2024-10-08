package AST.Stmt;

import AST.ASTVisitor;
import AST.Expr.ExprNode;
import Util.Position;
import Util.scope.loopScope;

public class WhileStmtNode extends StmtNode {
    public ExprNode condition=null;
    public StmtNode body=null;
    public loopScope scope;

    public WhileStmtNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
