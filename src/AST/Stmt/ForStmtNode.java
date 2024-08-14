package AST.Stmt;

import AST.ASTVisitor;
import AST.Expr.ExprNode;
import Util.Position;
import Util.scope.loopScope;

public class ForStmtNode extends StmtNode {
    public ExprNode condition,nextstep;
    public StmtNode initStmt,body;
    public loopScope scope;

    public ForStmtNode(Position pos){
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
