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

        for(var def:it.defNodes){
            def.accept(this);
        }
//        for(var v:it.varNodes){
//            v.accept(this);
//        }
//        for(var func:it.funcNodes){
//            func.accept(this);
//        }
//        for(var clas:it.classNodes){
//            clas.accept(this);
//        }
    }

    public void visit(ClassDefNode it){
        curScope=it.scope;
        for(var x:it.vars){
            x.accept(this);
        }
        for(var func:it.funcs){
            func.accept(this);
        }

        if(it.construct!=null){
            if(!it.construct.name.equals(it.name)){//判断构造函数名称是否正确
                throw new semanticError("Construct name mismatch",it.pos);
            }
            it.construct.accept(this);
        }
        curScope=curScope.parent;
    }

    public void visit(FuncDefNode it){
        curScope=it.scope;
        for(var stmt:it.body){
            if(stmt instanceof EmptyStmtNode) {//如果是空语句，就不访问
                stmt.accept(this);
            }
        }

        if(((funcScope)curScope).)
        curScope=curScope.parent;
    }

    public void visit(VarDefNode it){
        if(curScope==gScope){
            for(var x:it.vars){
                gScope.addVar(x.first,x.second.type,it.pos);
            }
        }
    }

    public void visit(ConstructNode it){
        curScope=new funcScope(curScope);
    }

    public void visit(ParalistNode it){
        for(var para:it.Paralist){
            if(para.first.isClass){//如果参数类型是类，则从globalScope中去找
                if(gScope.getClass(para.second)==null){
                    throw new semanticError("type "+para.second+" cannot be found",it.pos);
                }
            }
        }
    }

    public void visit(IfStmtNode it){


        if(it.trueStmt!=null){
            curScope=new Scope(curScope);
            it.trueStmt.accept(this);
            curScope=curScope.parent;
        }
        if(it.falseStmt!=null){
            curScope=new Scope(curScope);
            it.falseStmt.accept(this);
            curScope=curScope.parent;
        }
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
