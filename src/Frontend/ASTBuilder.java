package Frontend;

import AST.Def.*;
import AST.Expr.*;
import AST.Expr.BasicExpr.ArrayConstNode;
import AST.Expr.BasicExpr.BasicExprNode;
import AST.ProgramNode;
import AST.Stmt.*;
import AST.Type.Type;
import AST.Type.exprType;
import Parser.MxBaseVisitor;
import Parser.MxParser;
import AST.ASTNode;
import Util.Pair;
import Util.Position;
import Util.error.semanticError;
import Util.error.syntaxError;

public class ASTBuilder extends MxBaseVisitor<ASTNode> {
    @Override public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        ProgramNode program = new ProgramNode(new Position(ctx));
        for(var nodes:ctx.children){
            program.defNodes.add((DefNode) visit(nodes));
        }
//        for(var vardef:ctx.varDef()){
//            VarDefNode varDef = (VarDefNode) visit(vardef);
//            program.varNodes.add(varDef);
//        }
//        for(var funcdef:ctx.funcDef()){
//            FuncDefNode funcDef = (FuncDefNode) visit(funcdef);
//            program.funcNodes.add(funcDef);
//        }
//        for(var classdef:ctx.classDef()){
//            ClassDefNode classDef = (ClassDefNode) visit(classdef);
//            program.classNodes.add(classDef);
//        }
        return program;
    }

    @Override public ASTNode visitBasictype(MxParser.BasictypeContext ctx) {
        return visitChildren(ctx); 
    }

//    @Override public ASTNode visitTypename(MxParser.TypenameContext ctx) {
//
//    @Override public ASTNode visitType(MxParser.TypeContext ctx){
//    }
//
//    @Override public ASTNode visitReturntype(MxParser.ReturntypeContext ctx) {

    @Override public ASTNode visitVarDef(MxParser.VarDefContext ctx){
        VarDefNode varDef = new VarDefNode(new Position(ctx));
        varDef.vartype=new Type(ctx.type());
        for(var unit:ctx.varDefUnit()){
            if(unit.expression()==null){
                varDef.vars.add(new Pair<>(unit.Identifier().getText(),null));
            }else{
                ExprNode expr=(ExprNode) visit(unit.expression());
                varDef.vars.add(new Pair<>(unit.Identifier().getText(),expr));
            }
        }
        return varDef;
    }

    //函数定义
    @Override public ASTNode visitFuncDef(MxParser.FuncDefContext ctx) {
        FuncDefNode funcDefNode=new FuncDefNode(new Position(ctx));
        if(ctx.returntype().Void()!=null){
            funcDefNode.returntype=new Type("void",0);
        }else{
            Type type=new Type(ctx.returntype().type());
            funcDefNode.returntype=type;
        }
        funcDefNode.name=ctx.Identifier(0).getText();
        for(int i=1;i<ctx.Identifier().size();i++){//获取参数列表
            funcDefNode.paraslist.Paralist.add(new Pair<>(new Type(ctx.type(i-1)),ctx.Identifier(i).getText()));
        }
        for(var stmt:ctx.suite().statement()) {
            if(stmt instanceof MxParser.EmptyStmtContext) continue;//如果是空语句，则跳过
            funcDefNode.body.add((StmtNode) visit(stmt));
        }
        return funcDefNode;
    }

    //类构造函数
    @Override public ASTNode visitConstruct(MxParser.ConstructContext ctx) {
        ConstructNode constructNode=new ConstructNode(new Position(ctx));
        constructNode.name=ctx.Identifier().getText();
        for(var stmt:ctx.suite().statement()) {
            if(stmt instanceof MxParser.EmptyStmtContext) continue;//如果是空语句，则跳过
            constructNode.stmts.add((StmtNode) visit(stmt));
        }
        return constructNode;
    }

    //类定义
    @Override public ASTNode visitClassDef(MxParser.ClassDefContext ctx) {
        ClassDefNode classDefNode=new ClassDefNode(new Position(ctx));
        classDefNode.name=ctx.Identifier().getText();
        if(ctx.construct().size()>1){
            throw new syntaxError("More than one construct found",new Position(ctx));
        }else if(ctx.construct().size()==1){
            classDefNode.construct=(ConstructNode) visit(ctx.construct().get(0));
        }else{
            classDefNode.construct=null;
        }
        for(var vardef:ctx.varDef()){
            classDefNode.vars.add((VarDefNode) visit(vardef));
        }
        for(var funcdef:ctx.funcDef()){
            classDefNode.funcs.add((FuncDefNode) visit(funcdef));
        }
        return classDefNode;
    }

    @Override public ASTNode visitBlock(MxParser.BlockContext ctx) {
        BlockStmtNode block = new BlockStmtNode(new Position(ctx));
        for(var stmt:ctx.suite().statement()){
            if(stmt instanceof MxParser.EmptyStmtContext) continue;
            block.statements.add((StmtNode) visit(stmt));
        }
        return block;
    }

    @Override public ASTNode visitVardefStmt(MxParser.VardefStmtContext ctx) {
        VardefStmtNode vardefStmtNode=new VardefStmtNode(new Position(ctx));
        vardefStmtNode.varDef=(VarDefNode) visitVarDef(ctx.varDef());
        return vardefStmtNode;
    }

    @Override public ASTNode visitIfStmt(MxParser.IfStmtContext ctx) {
        IfStmtNode ifStmtNode=new IfStmtNode(new Position(ctx));
        ifStmtNode.condition=(ExprNode) visit(ctx.expression());
        ifStmtNode.trueStmt=(StmtNode) visit(ctx.trueStmt);
        if(ctx.falseStmt != null){
            ifStmtNode.falseStmt=(StmtNode) visit(ctx.falseStmt);
        }
        return ifStmtNode;
    }

    @Override public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        WhileStmtNode whileStmtNode=new WhileStmtNode(new Position(ctx));
        whileStmtNode.condition=(ExprNode) visit(ctx.expression());
        whileStmtNode.body=(StmtNode) visit(ctx.statement());
        return whileStmtNode;
    }

    @Override public ASTNode visitForStmt(MxParser.ForStmtContext ctx) {
        ForStmtNode forStmtNode=new ForStmtNode(new Position(ctx));
        if(ctx.initStmt != null){
            forStmtNode.initStmt=(StmtNode) visit(ctx.initStmt);
        }
        if(ctx.cond_!=null){
            forStmtNode.condition=(ExprNode) visit(ctx.cond_);
        }
        if(ctx.next_!=null){
            forStmtNode.nextstep=(ExprNode) visit(ctx.next_);
        }
        forStmtNode.body=(StmtNode) visit(ctx.statement(1));
        return forStmtNode;
    }

    @Override public ASTNode visitBreakStmt(MxParser.BreakStmtContext ctx) {
        return new BreakStmtNode(new Position(ctx));
    }

    @Override public ASTNode visitContinueStmt(MxParser.ContinueStmtContext ctx) {
        return new ContinueStmtNode(new Position(ctx));
    }

    @Override public ASTNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {
        ReturnStmtNode returnStmtNode=new ReturnStmtNode(new Position(ctx));
        if(ctx.expression()!=null){
            returnStmtNode.returnExpr=(ExprNode) visit(ctx.expression());
        }
        return returnStmtNode;
    }

    @Override public ASTNode visitPureExprStmt(MxParser.PureExprStmtContext ctx) {
        PureExprStmtNode pureExprStmtNode=new PureExprStmtNode(new Position(ctx));
        pureExprStmtNode.expr=(ExprNode) visit(ctx.expression());
        return pureExprStmtNode;
    }

    @Override public ASTNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
        return new EmptyStmtNode(new Position(ctx));
    }

    @Override public ASTNode visitNewVarExpr(MxParser.NewVarExprContext ctx) {
        NewVarExprNode newVarExprNode=new NewVarExprNode(new Position(ctx));
        newVarExprNode.type=new exprType(ctx.type());
        return newVarExprNode;
    }

    @Override public ASTNode visitFuncExpr(MxParser.FuncExprContext ctx) {
        FuncExprNode funcExprNode=new FuncExprNode(new Position(ctx));
        funcExprNode.func=(ExprNode) visit(ctx.expression(0));
        for(int i=1;i<ctx.expression().size();i++){
            funcExprNode.args.add((ExprNode) visit(ctx.expression(i)));
        }
        return funcExprNode;
    }

    //数组取下标操作
    @Override public ASTNode visitArrayExpr(MxParser.ArrayExprContext ctx) {
        ArrayExprNode arrayExprNode=new ArrayExprNode(new Position(ctx));
        arrayExprNode.array=(ExprNode) visit(ctx.expression(0));
        arrayExprNode.index=(ExprNode) visit(ctx.expression(1));
        return arrayExprNode;
    }

    @Override public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) {
        MemberExprNode memberExprNode=new MemberExprNode(new Position(ctx));
        memberExprNode.member=ctx.Identifier().getText();
        memberExprNode.obj=(ExprNode) visit(ctx.expression());
        return memberExprNode;
    }

    @Override public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        BinaryExprNode binaryExprNode=new BinaryExprNode(new Position(ctx));
        binaryExprNode.lhs=(ExprNode) visit(ctx.expression(0));
        binaryExprNode.rhs=(ExprNode) visit(ctx.expression(1));
        binaryExprNode.opCode=ctx.op.getText();
        return binaryExprNode;
    }

    @Override public ASTNode visitPreExpr(MxParser.PreExprContext ctx) {
        PreExprNode preExprNode=new PreExprNode(new Position(ctx));
        preExprNode.opCode=ctx.op.getText();
        preExprNode.expr=(ExprNode) visit(ctx.expression());
        return preExprNode;
    }

    @Override public ASTNode visitFStringExpr(MxParser.FStringExprContext ctx) {
        FStringExprNode fStringExprNode=new FStringExprNode(new Position(ctx));
        if(ctx.fstring().BasicFString()!=null){//不含有表达式
            fStringExprNode.stringlist.add(ctx.fstring().BasicFString().getText());
        }else{//含有表达式
            for(var expr:ctx.fstring().expression()){
                fStringExprNode.exprlist.add((ExprNode) visit(expr));
            }
            fStringExprNode.stringlist.add(ctx.fstring().FStringFront().getText());
            for(var mid:ctx.fstring().FStringMid()){
                fStringExprNode.stringlist.add(mid.getText());
            }
            fStringExprNode.stringlist.add(ctx.fstring().FStringEnd().getText());
        }
        return fStringExprNode;
    }

    @Override public ASTNode visitSufExpr(MxParser.SufExprContext ctx) {
        SufExprNode sufExprNode=new SufExprNode(new Position(ctx));
        sufExprNode.opCode=ctx.op.getText();
        sufExprNode.expr=(ExprNode) visit(ctx.expression());
        return sufExprNode;
    }

    @Override public ASTNode visitParenExpr(MxParser.ParenExprContext ctx) {
        ParenExprNode parenExprNode=new ParenExprNode(new Position(ctx));
        parenExprNode.expr=(ExprNode) visit(ctx.expression());
        return parenExprNode;
    }

    @Override public ASTNode visitBasicExpr(MxParser.BasicExprContext ctx) {
        if(ctx.primary().Identifier()!=null){
            BasicExprNode basicExprNode=new BasicExprNode(new Position(ctx));
            basicExprNode.isIdentifier=true;
            basicExprNode.name=ctx.primary().Identifier().getText();
            return basicExprNode;
        }else if(ctx.primary().This()!= null){
            BasicExprNode basicExprNode=new BasicExprNode(new Position(ctx));
            basicExprNode.isThis=true;
            return basicExprNode;
        }else{
            return visit(ctx.primary().const_());
        }
    }

    @Override public ASTNode visitUnaryExpr(MxParser.UnaryExprContext ctx){
        UnaryExprNode unaryExprNode=new UnaryExprNode(new Position(ctx));
        unaryExprNode.expr=(ExprNode) visit(ctx.expression());
        unaryExprNode.opCode=ctx.op.getText();
        return unaryExprNode;
    }

    @Override public ASTNode visitConditionExpr(MxParser.ConditionExprContext ctx) {
        ConditionExprNode conditionExprNode=new ConditionExprNode(new Position(ctx));
        conditionExprNode.cond_=(ExprNode) visit(ctx.expression(0));
        conditionExprNode.then_=(ExprNode) visit(ctx.expression(1));
        conditionExprNode.else_=(ExprNode) visit(ctx.expression(2));
        return conditionExprNode;
    }

    @Override public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        AssignExprNode assignExprNode=new AssignExprNode(new Position(ctx));
        assignExprNode.lhs=(ExprNode) visit(ctx.expression(0));
        assignExprNode.rhs=(ExprNode) visit(ctx.expression(1));
        return assignExprNode;
    }

    @Override public ASTNode visitNewArrayExpr(MxParser.NewArrayExprContext ctx) {
        NewArrayExprNode newArrayExprNode=new NewArrayExprNode(new Position(ctx));
        newArrayExprNode.type=new exprType(ctx.type());
        newArrayExprNode.type.dim=ctx.LeftBracket().size();
        //设定的长度必须连续出现在前面的[]中
        for(int i=0;i<ctx.expression().size();i++){
            if(ctx.expression(i).getStart().getTokenIndex()<ctx.LeftBracket(i).getSymbol().getTokenIndex()||ctx.expression(i).getStart().getTokenIndex()>ctx.RightBracket(i).getSymbol().getTokenIndex()){
                System.out.println("Invalid Identifier");
                throw new semanticError("new array error",new Position(ctx));
            }
            newArrayExprNode.sizelist.add((ExprNode) visit(ctx.expression(i)));
        }
        if(ctx.constArray()==null){
            newArrayExprNode.init=null;
        }else{
            newArrayExprNode.init=(ArrayConstNode) visit(ctx.constArray());
        }
        return newArrayExprNode;

    }

    @Override public ASTNode visitPrimary(MxParser.PrimaryContext ctx) {
        if(ctx.Identifier()!=null){
            BasicExprNode basicExprNode=new BasicExprNode(new Position(ctx));
            basicExprNode.isIdentifier=true;
            basicExprNode.name=ctx.Identifier().getText();
            return basicExprNode;
        }else if(ctx.This()!= null){
            BasicExprNode basicExprNode=new BasicExprNode(new Position(ctx));
            basicExprNode.isThis=true;
            return basicExprNode;
        }else{
            return visit(ctx.const_());
        }
    }

    //这个好像和上面的FStringExpr重复了
    @Override public ASTNode visitFstring(MxParser.FstringContext ctx) {
        FStringExprNode fStringExprNode=new FStringExprNode(new Position(ctx));
        if(ctx.BasicFString()!=null){//不含有表达式
            fStringExprNode.stringlist.add(ctx.BasicFString().getText());
        }else{//含有表达式
            for(var expr:ctx.expression()){
                fStringExprNode.exprlist.add((ExprNode) visit(expr));
            }
            fStringExprNode.stringlist.add(ctx.FStringFront().getText());
            for(var mid:ctx.FStringMid()){
                fStringExprNode.stringlist.add(mid.getText());
            }
            fStringExprNode.stringlist.add(ctx.FStringEnd().getText());
        }
        return fStringExprNode;
    }

    @Override public ASTNode visitConst(MxParser.ConstContext ctx) {
        BasicExprNode basicExprNode=new BasicExprNode(new Position(ctx));
        if(ctx.True()!= null){
            basicExprNode.isTrue=true;
            basicExprNode.value="1";
            return basicExprNode;
        }else if(ctx.False()!= null){
            basicExprNode.isFalse=true;
            basicExprNode.value="0";
            return basicExprNode;
        }else if(ctx.Null()!= null){
            basicExprNode.isNull=true;
            basicExprNode.value="null";
            return basicExprNode;
        }else if(ctx.DecimalInteger()!= null){
            basicExprNode.isInt=true;
            basicExprNode.value=ctx.DecimalInteger().getText();
            return basicExprNode;
        }else if(ctx.ConstString()!=null){
            basicExprNode.isString=true;
            basicExprNode.value=ctx.ConstString().getText();
            basicExprNode.value=basicExprNode.value.substring(1,basicExprNode.value.length()-1);
            return basicExprNode;
        }else{//数组常量
            return visit(ctx.constArray());
        }
    }

    @Override public ASTNode visitConstArray(MxParser.ConstArrayContext ctx) {
        ArrayConstNode arrayConstNode=new ArrayConstNode(new Position(ctx));
        for(var cst:ctx.const_()){
            arrayConstNode.elements.add((BasicExprNode) visit(cst));
        }
        return arrayConstNode;
    }
}
