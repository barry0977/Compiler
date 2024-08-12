package AST;

import AST.Expr.*;
import AST.Stmt.*;

import java.util.concurrent.locks.Condition;

public interface ASTVisitor {
    void visit(IfStmtNode it);
    void visit(WhileStmtNode it);
    void visit(ForStmtNode it);
    void visit(BreakStmtNode it);
    void visit(ContinueStmtNode it);
    void visit(PureExprStmtNode it);

    void visit(ArrayExprNode it);
    void visit(AssignExprNode it);
    void visit(BasicExprNode it);
    void visit(BinaryExprNode it);
    void visit(ConditionExprNode it);
    void visit(FuncExprNode it);
    void visit(MemberExprNode it);
    void visit(MemberFuncExprNode it);
    void visit(NewArrayExprNode it);
    void visit(NewVarExprNode it);
    void visit(UnaryExprNode it);
    void visit(PreExprNode it);
    void visit(SufExprNode it);
    void visit(ParenExprNode it);
}
