package AST;

import AST.Stmt.*;

public interface ASTVisitor {
    void visit(IfStmtNode it);
    void visit(WhileStmtNode it);
    void visit(ForStmtNode it);
    void visit(BreakStmtNode it);
    void visit(ContinueStmtNode it);
    void visit(PureExprStmtNode it);

    void visit();

}
