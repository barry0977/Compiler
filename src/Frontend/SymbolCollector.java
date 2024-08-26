package Frontend;

import AST.ASTVisitor;
import AST.Def.*;
import AST.Expr.*;
import AST.Expr.BasicExpr.ArrayConstNode;
import AST.Expr.BasicExpr.BasicExprNode;
import AST.ProgramNode;
import AST.Stmt.*;
import AST.Type.Type;
import Util.Decl.*;
import Util.scope.*;

public class SymbolCollector implements ASTVisitor {
    private globalScope gScope;
    private globalScope gScope2;//拷贝
    public Scope curScope;
    public Scope curScope2;

    public SymbolCollector(globalScope gScope, globalScope gScope2) {
        this.gScope = gScope;
        this.curScope=this.gScope;
        this.gScope2=gScope2;
        this.curScope2=this.gScope2;
    }

    @Override
    public void visit(ProgramNode node){
        for(var def:node.defNodes){
            def.accept(this);
        }
    }

    @Override
    public void visit(ClassDefNode it){
        gScope.addClass(it);
        gScope2.addClass(it);
        it.scope=new classScope(curScope);
        it.scope.classname=it.name;
        it.scope2=new classScope(curScope2);
        it.scope2.classname=it.name;
        curScope=it.scope;
        curScope2=it.scope2;
        for(var func:it.funcs){
            it.scope.addFunc(func);
            it.scope2.addFunc(func);
            visit(func);
        }
        //类中变量支持前向引用
        for(var x:it.vars){
            for(var v:x.vars) {
                if(v.second==null){
                    it.scope.addVar(v.first,x.vartype,x.pos);
                    it.scope2.addVar(v.first,x.vartype,x.pos);
                }else{
                    it.scope.addVar(v.first,x.vartype,x.pos);
                    it.scope2.addVar(v.first,x.vartype,x.pos);
                }
            }
        }
        curScope=it.scope.getParent();
        curScope2=it.scope2.getParent();
    }

    @Override
    public void visit(FuncDefNode it){
        if(curScope==gScope){
            gScope.addFunc(it);
            gScope2.addFunc(it);
        }
        it.scope=new funcScope(curScope);
        it.scope2=new funcScope(curScope2);
        curScope=it.scope;
        curScope2=it.scope2;
        for(var args:it.paraslist.Paralist){
            it.scope.addVar(args.second,args.first,it.pos);
            it.scope.params.put(args.second,args.first);

            it.scope2.addVar(args.second,args.first,it.pos);
            it.scope2.params.put(args.second,args.first);
        }
        it.scope.returnType=new Type(it.returntype);
        it.scope2.returnType=new Type(it.returntype);
        curScope=it.scope.getParent();
        curScope2=it.scope2.getParent();
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
