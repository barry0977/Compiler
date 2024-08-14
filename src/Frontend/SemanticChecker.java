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

    //还没处理好
    public void visit(FuncDefNode it){
        curScope=it.scope;
        for(var stmt:it.body){
            if(!(stmt instanceof EmptyStmtNode)) {//如果是空语句，就不访问
                stmt.accept(this);
            }
        }
        if(!((funcScope)curScope).isReturned&&!it.returntype.equals(new Type("void",0))){//如果没有Return语句并且函数返回值不是void
            throw new semanticError("non-void function has no Return Statement",it.pos);
        }
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
        curScope=new funcScope(curScope,new Type("void",0));
        for(var stmt:it.stmts){
            if(stmt instanceof EmptyStmtNode){
                stmt.accept(this);
            }
        }
        curScope=curScope.parent;
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
        if(it.condition!=null){
            it.condition.accept(this);
            if(!it.condition.type.equals(new Type("bool",0))){
                throw new semanticError("If condition is not bool",it.pos);
            }
        }
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
        curScope=new loopScope(curScope);
        if(it.condition!=null){
            it.condition.accept(this);
            if(!it.condition.type.equals(new Type("bool",0))){
                throw new semanticError("While condition is not bool",it.pos);
            }
        }
        if(it.body!=null){
            curScope=new Scope(curScope);
        }
        curScope=curScope.parent;
    }

    public void visit(ForStmtNode it){
        curScope=new loopScope(curScope);
        if(it.initStmt!=null){
            it.initStmt.accept(this);
        }
        if(it.condition!=null){
            it.condition.accept(this);
            if(!it.condition.type.equals(new Type("bool",0))){//条件表达式不是bool类型
                throw new semanticError("For condition is not bool",it.pos);
            }
        }
        if(it.nextstep!=null){
            it.nextstep.accept(this);
        }
        if(it.body!=null){
            it.body.accept(this);
        }
        curScope=curScope.parent;
    }

    public void visit(BreakStmtNode it){
        if(curScope.stype!= Scope.scopeType.loopscope){
            throw new semanticError("Break not in loop",it.pos);
        }
    }

    public void visit(ContinueStmtNode it){
        if(curScope.stype!= Scope.scopeType.loopscope){
            throw new semanticError("Continue not in loop",it.pos);
        }
    }

    public void visit(PureExprStmtNode it){
        it.expr.accept(this);
    }

    public void visit(ReturnStmtNode it){
        if(curScope.stype!=Scope.scopeType.funcscope){
            throw new semanticError("Return not in Function",it.pos);
        }
        ((funcScope) curScope).isReturned=true;
        if(it.returnExpr!=null){
            it.returnExpr.accept(this);
            if(!it.returnExpr.type.equals(((funcScope) curScope).returnType)){//返回表达式的类型与函数返回类型不符
                throw new semanticError("Return type mismatch",it.pos);
            }
        }else{
            if(!((funcScope) curScope).returnType.equals(new Type("void",0))){//如果函数返回类型不是void，则报错
                throw new semanticError("Return type mismatch",it.pos);
            }
        }
    }

    public void visit(BlockStmtNode it){
        curScope=new Scope(curScope);
        for(var stmt:it.statements){
            if(stmt != null){
                stmt.accept(this);
            }
        }
        curScope=curScope.parent;
    }

    //上面已经实现过
    public void visit(VardefStmtNode it){
        it.varDef.accept(this);
    }

    //空语句不需要操作
    public void visit(EmptyStmtNode it){}

    void visit(ArrayExprNode it);
    public void visit(AssignExprNode it){
        it.lhs.accept(this);
        it.rhs.accept(this);
        if(!it.lhs.isLeftValue){
            throw new semanticError("lhs not left value",it.pos);
        }
        if(!it.lhs.type.equals(it.rhs.type)){
            throw new semanticError("assign type mismatch",it.pos);
        }
        it.type=new Type(it.rhs.type);
        it.isLeftValue=false;
    }

    public void visit(BasicExprNode it){
        //TODO
    }

    public void visit(BinaryExprNode it){
        it.lhs.accept(this);
        it.rhs.accept(this);

        if(!it.lhs.type.equals(it.rhs.type)){//左右表达式类型不同
            throw new semanticError("lhs and rhs type mismatch",it.pos);
        }

        if(it.lhs.type.isBool){//bool 类型仅可做 ==、!=、&& 以及 || 运算
            if(it.opCode.equals("==")||it.opCode.equals("!=")||it.opCode.equals("&&")||it.opCode.equals("||")){
                it.type=new Type("bool",0);
                it.isLeftValue=false;
                return;
            }else{
                throw new semanticError("bool operation wrong",it.pos);
            }
        }

        if(it.lhs.type.dim>0||it.rhs.type.dim>0){//数组（lhs或rhs可以为null）
            if(it.opCode.equals("==")||it.opCode.equals("!=")){
                it.type=new Type("bool",0);
                it.isLeftValue=false;
                return;
            }else{
                throw new semanticError("array operation wrong",it.pos);
            }
        }
        //string
        if(it.lhs.type.isString){
            if(it.opCode.equals("+")){
                it.type=new Type("string",0);
                it.isLeftValue=false;
                return;
            }else if(it.opCode.equals("==")||it.opCode.equals("!=")||it.opCode.equals("<")||it.opCode.equals(">")||it.opCode.equals("<=")||it.opCode.equals(">=")){
                it.type=new Type("bool",0);
                it.isLeftValue=false;
                return;
            }else{
                throw new semanticError("string operation wrong",it.pos);
            }
        }
        //int
        if(it.opCode.equals("==")||it.opCode.equals("!=")||it.opCode.equals("<")||it.opCode.equals(">")||it.opCode.equals("<=")||it.opCode.equals(">=")){
            it.type=new Type("bool",0);
            it.isLeftValue=false;
            return;
        }else{
            it.type=new Type("int",0);
            it.isLeftValue=false;
            return;
        }
    }

    public void visit(ConditionExprNode it){
        it.cond_.accept(this);
        it.then_.accept(this);
        it.else_.accept(this);
        if(!it.cond_.type.isBool){
            throw new semanticError("ConditionExpr condition not bool",it.pos);
        }
        if(!it.then_.type.equals(it.else_.type)){
            throw new semanticError("ConditionExpr type mismatch",it.pos);
        }
        it.type=new Type(it.cond_.type);
        it.isLeftValue=false;
    }

    void visit(FuncExprNode it);
    void visit(MemberExprNode it);
    void visit(MemberFuncExprNode it);
    void visit(NewArrayExprNode it);
    void visit(NewVarExprNode it);
    public void visit(UnaryExprNode it){
        it.expr.accept(this);
        if(it.opCode.equals("-")||it.opCode.equals("~")){
            if(it.expr.type.isInt){
                it.type=new Type("int",0);
                it.isLeftValue=false;
                return;
            }else{
                throw new semanticError("unary operation not int",it.pos);
            }
        }else if(it.opCode.equals("!")){
            if(it.expr.type.isBool){
                it.type=new Type("bool",0);
                it.isLeftValue=false;
                return;
            }else{
                throw new semanticError("unary operation not bool",it.pos);
            }
        }else{
            throw new semanticError("unary operation op wrong",it.pos);
        }
    }

    public void visit(PreExprNode it){
        it.expr.accept(this);
        if(!it.expr.type.isInt){//只有int能进行自加自减运算
            throw new semanticError("pre expression not int",it.pos);
        }
        if(!it.expr.isLeftValue){//必须是左值才能自加自减
            throw new semanticError("pre expression not left value",it.pos);
        }
        it.type=new Type("int",0);
        it.isLeftValue=true;
    }

    public void visit(SufExprNode it){
        it.expr.accept(this);
        if(!it.expr.type.isInt){//只有int能进行自加自减运算
            throw new semanticError("pre expression not int",it.pos);
        }
        if(!it.expr.isLeftValue){//必须是左值才能自加自减
            throw new semanticError("pre expression not left value",it.pos);
        }
        it.type=new Type("int",0);
        it.isLeftValue=true;
    }

    public void visit(ParenExprNode it){//加括号不会改变表达式性质
        it.expr.accept(this);
        it.type=new Type(it.expr.type);
        it.isLeftValue=it.expr.isLeftValue;
    }

    public void visit(FStringExprNode it){
        //TODO
    }

    void visit(ArrayConstNode it);
}
