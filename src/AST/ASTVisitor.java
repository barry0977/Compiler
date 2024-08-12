package AST;

import AST.Def.ClassDefNode;
import AST.Def.ConstructNode;
import AST.Def.FuncDefNode;
import AST.Def.VarDefNode;
import AST.Expr.*;
import AST.Stmt.*;

import java.util.concurrent.locks.Condition;

public interface ASTVisitor {
    void visit(ProgramNode it);
    void visit(ClassDefNode it);
    void visit(FuncDefNode it);
    void visit(VarDefNode it);
    void visit(ConstructNode it);

    void visit(IfStmtNode it);
    void visit(WhileStmtNode it);
    void visit(ForStmtNode it);
    void visit(BreakStmtNode it);
    void visit(ContinueStmtNode it);
    void visit(PureExprStmtNode it);
    void visit(ReturnStmtNode it);
    void visit(BlockStmtNode it);

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
    void visit(FStringExprNode it);
}
