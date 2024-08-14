package Frontend;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Def.*;
import AST.Expr.*;
import AST.Expr.BasicExpr.ArrayConstNode;
import AST.Expr.BasicExpr.BasicExprNode;
import AST.ProgramNode;
import AST.Stmt.*;
import AST.Type.Type;
import Util.Decl.FuncDecl;
import Util.error.*;
import Util.scope.*;


public class SemanticChecker implements ASTVisitor {
    globalScope gScope;
    Scope curScope;

    public SemanticChecker(globalScope gScope) {
        this.gScope = gScope;
        curScope=gScope;
    }

    public void visit(ProgramNode it){
        FuncDecl mainFunc=gScope.getFunc("main");
        if(mainFunc==null){//没有main函数
            throw new semanticError("No Main Function",it.pos);
        }else if(!mainFunc.returnType.equals(new Type("int",0))){//main函数返回值不是int
            throw new semanticError("Main Function returns wrong type",it.pos);
        }else if(!mainFunc.params.isEmpty()){
            throw new semanticError("Main Function parameters are not allowed",it.pos);
        }

        for(var v:it.varNodes){
            v.accept(this);
        }
        for(var func:it.funcNodes){
            func.accept(this);
        }
        for(var clas:it.classNodes){
            clas.accept(this);
        }
    }

    public void visit(ClassDefNode it){

    }

    public void visit(FuncDefNode it){

    }

    public void visit(VarDefNode it){

    }

    public void visit(ConstructNode it){

    }

    public void visit(ParalistNode it){

    }

    public void visit(IfStmtNode it){

    }

    public void visit(WhileStmtNode it){

    }
    void visit(ForStmtNode it);
    void visit(BreakStmtNode it);
    void visit(ContinueStmtNode it);
    void visit(PureExprStmtNode it);
    void visit(ReturnStmtNode it);
    void visit(BlockStmtNode it);
    void visit(VardefStmtNode it);
    void visit(EmptyStmtNode it);

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

    void visit(ArrayConstNode it);
}
