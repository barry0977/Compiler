package Frontend;

import AST.ASTVisitor;
import AST.Def.*;
import AST.Expr.*;
import AST.Expr.BasicExpr.ArrayConstNode;
import AST.Expr.BasicExpr.BasicExprNode;
import AST.ProgramNode;
import AST.Stmt.*;
import Util.Decl.ClassDecl;
import Util.Decl.FuncDecl;
import Util.scope.globalScope;

public class SymbolCollector implements ASTVisitor {
    private globalScope gScope;

    public SymbolCollector(globalScope gScope) {
        this.gScope = gScope;
    }

    @Override
    public void visit(ProgramNode node){
        for(var func:node.funcNodes){
            gScope.funcDecls.put(func.funcname,new FuncDecl(func));
        }
        for(var clas:node.classNodes){
            gScope.classDecls.put(clas.name,new ClassDecl(clas));
        }
        for(var x:node.varNodes){
            for(var v:x.vars) {
                gScope.vars.put(v.first,x.vartype);
            }
        }
    }

    public void visit(ClassDefNode it){}
    public void visit(FuncDefNode it){}
    public void visit(VarDefNode it) { }
    public void visit(ConstructNode it) { }
    public void visit(ParalistNode it) { }

    public void visit(IfStmtNode it) { }
    public void visit(WhileStmtNode it) { }
    public void visit(ForStmtNode it) { }
    public void visit(BreakStmtNode it) { }
    public void visit(ContinueStmtNode it) { }
    public void visit(PureExprStmtNode it) { }
    public void visit(ReturnStmtNode it) { }
    public void visit(BlockStmtNode it) { }
    public void visit(VardefStmtNode it) { }
    public void visit(EmptyStmtNode it) { }

    public void visit(ArrayExprNode it) { }
    public void visit(AssignExprNode it) { }
    public void visit(BasicExprNode it) { }
    public void visit(BinaryExprNode it) { }
    public void visit(ConditionExprNode it) { }
    public void visit(FuncExprNode it) { }
    public void visit(MemberExprNode it) { }
    public void visit(MemberFuncExprNode it) { }
    public void visit(NewArrayExprNode it) { }
    public void visit(NewVarExprNode it) { }
    public void visit(UnaryExprNode it) { }
    public void visit(PreExprNode it) { }
    public void visit(SufExprNode it) { }
    public void visit(ParenExprNode it) { }
    public void visit(FStringExprNode it) { }

    public void visit(ArrayConstNode it) { }
}
