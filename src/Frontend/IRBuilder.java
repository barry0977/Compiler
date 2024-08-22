package Frontend;

import AST.ASTVisitor;
import AST.Def.*;
import AST.Expr.*;
import AST.Expr.BasicExpr.ArrayConstNode;
import AST.Expr.BasicExpr.BasicExprNode;
import AST.ProgramNode;
import AST.Stmt.*;
import IR.IRBlock;
import IR.module.*;
import IR.IRProgram;
import IR.type.IRType;
import Util.scope.*;

public class IRBuilder implements ASTVisitor {
    IRProgram program;
    globalScope gScope;
    Scope curScope;
    IRBlock curBlock;
    IRBlock curEntry;//如果是函数，则有entry块

    public IRBuilder(IRProgram program, globalScope gscope) {
        this.program = program;
        this.gScope = gscope;
        this.curScope = gscope;
    }

    public void visit(ProgramNode it){
        for(var def:it.defNodes){
            def.accept(this);
        }
    }

    public void visit(ClassDefNode it){
        curScope=it.scope;
        IRClassDef classDef = new IRClassDef();
        classDef.name=it.name;
        for(var mem:it.vars){
            classDef.members.add(new IRType(mem.vartype));
        }
        for(var func:it.funcs){
            func.accept(this);
        }
        curScope=curScope.parent;
    }

    public void visit(FuncDefNode it){
        curScope=it.scope;
        if(curScope instanceof globalScope){//全局函数
            IRFuncDef funcDef=new IRFuncDef();
            funcDef.name=it.name;

            for(var stmt:it.body){
                stmt.accept(this);
            }
        }else{//类方法，名字修改，参数要加上this
            IRFuncDef funcDef=new IRFuncDef();
            funcDef.name=((classScope) curScope.parent).classname+"::"+it.name;//名字前加上类名
            for(var stmt:it.body){
                stmt.accept(this);
            }
        }
        curScope=curScope.parent;
    }

    public void visit(VarDefNode it){
        if(curScope instanceof globalScope){//全局变量
            IRGlobalVarDef newvar = new IRGlobalVarDef();
            newvar.name=it.name;
            newvar.type=new IRType(it.vartype);

        }else{//局部变量

        }
    }

    public void visit(ConstructNode it){
    }

    public void visit(ParalistNode it){
    }

    public void visit(IfStmtNode it){
    }

    public void visit(WhileStmtNode it){
    }

    public void visit(ForStmtNode it){
    }

    public void visit(BreakStmtNode it){

    }

    public void visit(ContinueStmtNode it){
    }

    public void visit(PureExprStmtNode it){
    }

    public void visit(ReturnStmtNode it){
    }

    public void visit(BlockStmtNode it){
    }

    public void visit(VardefStmtNode it){
    }

    public void visit(EmptyStmtNode it){

    }

    public void visit(ArrayExprNode it){
    }

    public void visit(AssignExprNode it){
    }

    public void visit(BasicExprNode it){
    }

    public void visit(BinaryExprNode it){
        it.lhs.accept(this);
        it.rhs.accept(this);

    }

    public void visit(ConditionExprNode it){
    }

    public void visit(FuncExprNode it){
    }

    public void visit(MemberExprNode it) {
    }

    public void visit(NewArrayExprNode it){
    }

    public void visit(NewVarExprNode it){
    }

    public void visit(UnaryExprNode it){
    }

    public void visit(PreExprNode it){
    }

    public void visit(SufExprNode it){
    }

    public void visit(ParenExprNode it){
    }

    public void visit(FStringExprNode it){
    }

    public void visit(ArrayConstNode it) {
    }
}
