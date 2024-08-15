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
import AST.Type.exprType;
import Util.Decl.ClassDecl;
import Util.Decl.FuncDecl;
import Util.Pair;
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
        if(!((funcScope)curScope).isReturned&&!it.returntype.equals(new Type("void",0))&&!it.name.equals("main")){//如果没有Return语句并且函数返回值不是void(main函数可以没有返回值)
            System.out.println("Missing Return Statement");
            throw new semanticError("non-void function has no Return Statement",it.pos);
        }
        curScope=curScope.parent;
    }

    public void visit(VarDefNode it){
        if(it.vartype.isClass){//判断用的类是否定义过
            ClassDecl class_=gScope.classDecls.get(it.vartype.typeName);
            if(class_==null){
                throw new semanticError("Class "+it.vartype.typeName+" not found",it.pos);
            }
        }
        for(var x:it.vars){
            if(x.second!=null){
                x.second.accept(this);
                if(!x.second.type.equals(it.vartype)){
                    throw new semanticError("initial type mismatch",it.pos);
                }
            }
            if(!(curScope instanceof classScope)){//类里面的变量symbolcollect时已经加过了
                curScope.addVar(x.first,it.vartype,it.pos);
            }
        }
    }

    public void visit(ConstructNode it){
        curScope=new funcScope(curScope,new Type("void",0));
        for(var stmt:it.stmts){
            if(!(stmt instanceof EmptyStmtNode)){
                stmt.accept(this);
            }
        }
        curScope=curScope.parent;
    }

    public void visit(ParalistNode it){
        for(var para:it.Paralist){
            if(para.first.isClass){//如果参数类型是类，则从globalScope中去找
                if(gScope.getClass(para.second)==null){
                    System.out.println("Undefined Identifier");
                    throw new semanticError("type "+para.second+" cannot be found",it.pos);
                }
            }
        }
    }

    public void visit(IfStmtNode it){
        if(it.condition!=null){
            it.condition.accept(this);
            if(!it.condition.type.equals(new Type("bool",0))){
                System.out.println("Invalid Type");
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
        if(it.condition!=null){
            it.condition.accept(this);
            if(!it.condition.type.equals(new Type("bool",0))){
                System.out.println("Invalid Type");
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
                System.out.println("Invalid Type");
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
        if(!curScope.inLoop()){
            System.out.println("Invalid Control Flow");
            throw new semanticError("Break not in loop",it.pos);
        }
    }

    public void visit(ContinueStmtNode it){
        if(!curScope.inLoop()){
            System.out.println("Invalid Control Flow");
            throw new semanticError("Continue not in loop",it.pos);
        }
    }

    public void visit(PureExprStmtNode it){
        it.expr.accept(this);
    }

    public void visit(ReturnStmtNode it){
        if(!curScope.inFunc()){
            throw new semanticError("Return not in Function",it.pos);
        }
//        ((funcScope) curScope).isReturned=true;
        curScope.setReturn();
        if(it.returnExpr!=null){
            it.returnExpr.accept(this);
            if(!it.returnExpr.type.equals(curScope.getReturnType())){//返回表达式的类型与函数返回类型不符
                System.out.println("Type Mismatch");
                throw new semanticError("Return type mismatch",it.pos);
            }
        }else{
            if(!curScope.getReturnType().equals(new Type("void",0))){//如果函数返回类型不是void，则报错
                System.out.println("Type Mismatch");
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

    public void visit(ArrayExprNode it){
        it.array.accept(this);
        it.index.accept(this);
        if(!(it.index.type.isInt&&it.index.type.dim==0)){
            throw new semanticError("Array index not int",it.pos);
        }
        if(it.array.type.dim==0){
            System.out.println("Dimension Out Of Bound");
            throw new semanticError("Array dimension is zero",it.pos);
        }
        it.type=new exprType(it.array.type);
        it.type.dim--;//维度减1
        it.isLeftValue=true;
    }

    public void visit(AssignExprNode it){
        it.lhs.accept(this);
        it.rhs.accept(this);
        if(!it.lhs.isLeftValue){
            System.out.println("Type Mismatch");
            throw new semanticError("lhs not left value",it.pos);
        }
        //注意如果右表达式为null的情况
//        if(it.rhs.type.isNull) {
//
//        }
        if(!it.lhs.type.equals(it.rhs.type)){
            System.out.println("Type Mismatch");
            throw new semanticError("assign type mismatch",it.pos);
        }
        it.type=new exprType(it.rhs.type);
        it.isLeftValue=false;
    }

    public void visit(BasicExprNode it){
        if(it.isInt){
            it.type=new exprType("int",0);
            it.isLeftValue=false;
        }else if(it.isTrue||it.isFalse){
            it.type=new exprType("bool",0);
            it.isLeftValue=false;
        }else if(it.isString){
            it.type=new exprType("string",0);
            it.isLeftValue=false;
        }else if(it.isNull){
            it.type=new exprType("null",0);
            it.isLeftValue=false;
        }else if(it.isThis){
            String inclass=curScope.inClass();
            if(inclass==null){
                throw new semanticError("This not in class",it.pos);
            }
            it.type=new exprType(inclass,0);
            it.isLeftValue=false;
        }else if(it.isIdentifier){
            it.type=curScope.getIdentifier(it.name);
            if(it.type==null){
                System.out.println("Undefined Identifier");
                throw new semanticError("Variable "+it.name+" not found",it.pos);
            }
            if(it.type.isFunc){
                it.isLeftValue=false;
            }else{
                it.isLeftValue=true;
            }
        }else{
            System.err.println("Unknown expression type");
        }
    }

    public void visit(BinaryExprNode it){
        it.lhs.accept(this);
        it.rhs.accept(this);

        if(!it.lhs.type.equals(it.rhs.type)){//左右表达式类型不同
            System.out.println("Type Mismatch");
            throw new semanticError("lhs and rhs type mismatch",it.pos);
        }

        if(it.lhs.type.isBool){//bool 类型仅可做 ==、!=、&& 以及 || 运算
            if(it.opCode.equals("==")||it.opCode.equals("!=")||it.opCode.equals("&&")||it.opCode.equals("||")){
                it.type=new exprType("bool",0);
                it.isLeftValue=false;
                return;
            }else{
                System.out.println("Invalid Type");
                throw new semanticError("bool operation wrong",it.pos);
            }
        }

        if(it.lhs.type.dim>0||it.rhs.type.dim>0){//数组（lhs或rhs可以为null）
            if(it.opCode.equals("==")||it.opCode.equals("!=")){
                it.type=new exprType("bool",0);
                it.isLeftValue=false;
                return;
            }else{
                System.out.println("Invalid Type");
                throw new semanticError("array operation wrong",it.pos);
            }
        }
        //string
        if(it.lhs.type.isString){
            if(it.opCode.equals("+")){
                it.type=new exprType("string",0);
                it.isLeftValue=false;
                return;
            }else if(it.opCode.equals("==")||it.opCode.equals("!=")||it.opCode.equals("<")||it.opCode.equals(">")||it.opCode.equals("<=")||it.opCode.equals(">=")){
                it.type=new exprType("bool",0);
                it.isLeftValue=false;
                return;
            }else{
                System.out.println("Invalid Type");
                throw new semanticError("string operation wrong",it.pos);
            }
        }
        //int
        if(it.lhs.type.isInt){
            if(it.opCode.equals("==")||it.opCode.equals("!=")||it.opCode.equals("<")||it.opCode.equals(">")||it.opCode.equals("<=")||it.opCode.equals(">=")){
                it.type=new exprType("bool",0);
            }else{
                it.type=new exprType("int",0);
            }
            it.isLeftValue=false;
            return;
        }
        //class
        if(it.lhs.type.isClass){
            if(it.opCode.equals("==")||it.opCode.equals("!=")){
                it.type=new exprType("bool",0);
                it.isLeftValue=false;
                return;
            }else{
                System.out.println("Invalid Type");
                throw new semanticError("class operation wrong",it.pos);
            }
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
        it.type=new exprType(it.then_.type);
        it.isLeftValue=false;
    }

    public void visit(FuncExprNode it){
        it.func.accept(this);
        for(var arg:it.args){
            arg.accept(this);
        }
        if(!it.func.type.isFunc){//调用的不是函数
            throw new semanticError("Func operation not function",it.pos);
        }
        FuncDecl func=null;
        if(it.func instanceof BasicExprNode){//如果是普通函数，则去scope中找
            func=curScope.getFunc(it.func.type.funcinfo.name,true);
        }else if(it.func instanceof MemberExprNode){//如果是成员函数，则之前MemberExpr已经确定
            func=it.func.type.funcinfo;
        }
//        FuncDecl func=curScope.getFunc(it.func.type.funcinfo.name,true);//从scope中去找函数，找不到就往上一层
        if(func==null){
            System.out.println("Undefined Identifier");
            throw new semanticError("Func does not exist: "+it.func.type.funcinfo.name,it.pos);
        }
        if(func.params.size()!=it.args.size()){
            throw new semanticError("Func params number mismatch",it.pos);
        }else{
            for(int i=0;i<it.args.size();i++){
                if(!it.args.get(i).type.equals(func.params.get(i))){
                    System.out.println("Type Mismatch");
                    throw new semanticError("Func params mismatch",it.pos);
                }
            }
        }
        it.type=new exprType(it.func.type.funcinfo.returnType);//如果参数匹配，则类型为函数返回值
        it.isLeftValue=false;
    }

    public void visit(MemberExprNode it) {
        it.obj.accept(this);
        if (it.obj.type.dim > 0) {//数组类型只能调用.size()
            if (!it.member.equals("size")) {
                throw new semanticError("member function for array is not size()", it.pos);
            } else {
                it.type = new exprType("size", new FuncDecl("size", new Type("int", 0), "", "", 0));
                it.isLeftValue = false;
                return;
            }
        }
        //寻找所在的类
        ClassDecl class_ = gScope.getClass(it.obj.type.typeName);//从全局变量里面找到类
        //是否是变量
        Type var = class_.vars.get(it.member);
        if (var != null) {
            it.type = new exprType(var);
            it.isLeftValue = true;//数组对象的元素可作为左值
            return;
        }
        //是否是函数
        FuncDecl func_ = class_.funcs.get(it.member);
        if (func_ != null) {
            it.type = new exprType(it.member, func_);
            it.isLeftValue = false;
            return;
        }
        System.out.println("Undefined Identifier");
        throw new semanticError("member not found", it.pos);
    }

    public void visit(NewArrayExprNode it){
        for(var len:it.sizelist){
            len.accept(this);
        }
        if(it.init!=null){
            it.init.accept(this);
        }
        if(it.type.isClass){
            ClassDecl class_ = gScope.getClass(it.type.typeName);
            if(class_==null){
                System.out.println("Undefined Identifier");
                throw new semanticError("class not found",it.pos);
            }
        }
        //确认每个size都是int
        for(var len:it.sizelist){
            if(!len.type.isInt){
                System.out.println("Type Mismatch");
                throw new semanticError("array size not int",it.pos);
            }
        }
        //如果有初始值
        if(it.init!=null){
            if(it.init.type.isNull){//如果初始值为null
                if(it.type.dim<it.init.type.dim){
                    System.out.println("Type Mismatch");
                    throw new semanticError("array dim out of range",it.pos);
                }else{
                    it.type=new exprType(it.init.type);
                    it.isLeftValue=false;
                }
            }else{//需要判断
                if(!it.type.equals(it.init.type)){
                    System.out.println("Type Mismatch");
                    throw new semanticError("array type mismatch",it.pos);
                }else{
                    it.type=new exprType(it.init.type);
                    it.isLeftValue=false;
                }
            }
        }
    }

    public void visit(NewVarExprNode it){
        if(it.type.isClass){
            ClassDecl class_=gScope.getClass(it.type.typeName);
            if(class_==null){
                System.out.println("Undefined Identifier");
                throw new semanticError("class not found in new var",it.pos);
            }
        }
    }

    public void visit(UnaryExprNode it){
        it.expr.accept(this);
        if(it.opCode.equals("-")||it.opCode.equals("~")){
            if(it.expr.type.isInt){
                it.type=new exprType("int",0);
                it.isLeftValue=false;
            }else{
                System.out.println("Type Mismatch");
                throw new semanticError("unary operation not int",it.pos);
            }
        }else if(it.opCode.equals("!")){
            if(it.expr.type.isBool){
                it.type=new exprType("bool",0);
                it.isLeftValue=false;
            }else{
                System.out.println("Type Mismatch");
                throw new semanticError("unary operation not bool",it.pos);
            }
        }else{
            throw new semanticError("unary operation op wrong",it.pos);
        }
    }

    public void visit(PreExprNode it){
        it.expr.accept(this);
        if(!it.expr.type.isInt){//只有int能进行自加自减运算
            System.out.println("Type Mismatch");
            throw new semanticError("pre expression not int",it.pos);
        }
        if(!it.expr.isLeftValue){//必须是左值才能自加自减
            System.out.println("Type Mismatch");
            throw new semanticError("pre expression not left value",it.pos);
        }
        it.type=new exprType("int",0);
        it.isLeftValue=true;
    }

    public void visit(SufExprNode it){
        it.expr.accept(this);
        if(!it.expr.type.isInt){//只有int能进行自加自减运算
            System.out.println("Type Mismatch");
            throw new semanticError("pre expression not int",it.pos);
        }
        if(!it.expr.isLeftValue){//必须是左值才能自加自减
            System.out.println("Type Mismatch");
            throw new semanticError("pre expression not left value",it.pos);
        }
        it.type=new exprType("int",0);
        it.isLeftValue=false;//a++不是左值
    }

    public void visit(ParenExprNode it){//加括号不会改变表达式性质
        it.expr.accept(this);
        it.type=new exprType(it.expr.type);
        it.isLeftValue=it.expr.isLeftValue;
    }

    public void visit(FStringExprNode it){
        for(var expr:it.exprlist){
            expr.accept(this);
        }
        for(var expr:it.exprlist){
            if(!(expr.type.isInt||expr.type.isBool||expr.type.isString)){
                throw new semanticError("fstring expr type wrong",it.pos);
            }
        }
        it.type=new exprType("string",0);
        it.isLeftValue=false;
    }

    public void visit(ArrayConstNode it){
        for(var elem:it.elements){
            elem.accept(this);
        }
        if(it.elements.isEmpty()){//{ }
            it.type=new exprType("null",0);
            return;
        }
        exprType firsttype=it.elements.get(0).type;
        if(firsttype.isFunc){
            System.out.println("Type Mismatch");
            throw new semanticError("function should not be in array",it.pos);
        }
        it.type=new exprType(firsttype);//先将type定为第一个元素的type
        it.isLeftValue=false;
        for(var elem:it.elements){
            if(it.type.isNull){//如果现在是null，则后面可以是一切类型
                if(elem.type.isNull){//如果都是null，取较大维数的
                    it.type.dim= Math.max(it.type.dim, elem.type.dim);
                }else{//如果遇到不是null的，如果维数正确则修改type
                    if(elem.type.dim<it.type.dim){
                        System.out.println("Type Mismatch");
                        throw new semanticError("array element dimension mismatch",it.pos);
                    }else{
                        it.type=new exprType(elem.type);
                    }
                }
            }else{//如果不是null，则需要比较
                if(elem.type.isNull){
                    if(elem.type.dim>it.type.dim){
                        System.out.println("Type Mismatch");
                        throw new semanticError("array element dimension mismatch",it.pos);
                    }
                }else{
                    if(!it.type.equals(elem.type)){
                        System.out.println("Type Mismatch");
                        throw new semanticError("array element type mismatch",it.pos);
                    }
                }
            }
        }
        it.type.dim+=1;
    }
}
