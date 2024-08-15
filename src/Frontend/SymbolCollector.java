package Frontend;

import AST.ASTVisitor;
import AST.Def.*;
import AST.Expr.*;
import AST.Expr.BasicExpr.ArrayConstNode;
import AST.Expr.BasicExpr.BasicExprNode;
import AST.ProgramNode;
import AST.Stmt.*;
import Util.Decl.*;
import Util.scope.*;

public class SymbolCollector implements ASTVisitor {
    private globalScope gScope;
    public Scope curScope;

    public SymbolCollector(globalScope gScope) {
        this.gScope = gScope;
        this.curScope=this.gScope;
    }

    @Override
    public void visit(ProgramNode node){
        for(var def:node.defNodes){
            def.accept(this);
        }
//        for(var clas:node.classNodes){
//            gScope.addClass(clas);
//            visit(clas);
//        }
//        for(var x:node.varNodes){
//            for(var v:x.vars) {
//                gScope.vars.put(v.first,x.vartype);
//            }
//            visit(x);
//        }
    }

    @Override
    public void visit(ClassDefNode it){
        gScope.addClass(it);
        it.scope=new classScope(curScope);
        curScope=it.scope;
        for(var func:it.funcs){
            it.scope.addFunc(func);
            visit(func);
        }
        //类中变量支持前向引用
        for(var x:it.vars){
            for(var v:x.vars) {
                it.scope.addVar(v.first,v.second.type,x.pos);
            }
        }
        curScope=it.scope.getParent();
    }

    @Override
    public void visit(FuncDefNode it){
        if(curScope==gScope){
            gScope.addFunc(it);
        }
        it.scope=new funcScope(curScope);
        curScope=it.scope;
        for(var args:it.paraslist.Paralist){
            it.scope.addVar(args.second,args.first,it.pos);
            it.scope.params.put(args.second,args.first);
        }
        curScope=it.scope.getParent();
    }
    //全局变量和局部变量不支持前向引用，不用加进去
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
    public void visit(NewArrayExprNode it) { }
    public void visit(NewVarExprNode it) { }
    public void visit(UnaryExprNode it) { }
    public void visit(PreExprNode it) { }
    public void visit(SufExprNode it) { }
    public void visit(ParenExprNode it) { }
    public void visit(FStringExprNode it) { }

    public void visit(ArrayConstNode it) { }
}
