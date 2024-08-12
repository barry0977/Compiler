package Frontend;

import AST.Def.ClassDefNode;
import AST.Def.ConstructNode;
import AST.Def.FuncDefNode;
import AST.Def.VarDefNode;
import AST.Expr.ExprNode;
import AST.ProgramNode;
import AST.Stmt.*;
import AST.Type.Type;
import Parser.MxBaseVisitor;
import Parser.MxParser;
import Parser.MxLexer;
import AST.ASTNode;
import Util.Pair;
import Util.Position;
import Util.error.Error;

public class ASTBuilder extends MxBaseVisitor<ASTNode> {
    @Override public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        ProgramNode program = new ProgramNode(new Position(ctx));
        for(var vardef:ctx.varDef()){
            VarDefNode varDef = (VarDefNode) visit(vardef);
            program.varNodes.add(varDef);
        }
        for(var funcdef:ctx.funcDef()){
            FuncDefNode funcDef = (FuncDefNode) visit(funcdef);
            program.funcNodes.add(funcDef);
        }
        for(var classdef:ctx.classDef()){
            ClassDefNode classDef = (ClassDefNode) visit(classdef);
            program.classNodes.add(classDef);
        }
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
            funcDefNode.returntype.isVoid=true;
        }else{
            Type type=new Type(ctx.returntype().type());
            funcDefNode.returntype=type;
        }
        funcDefNode.funcname=ctx.Identifier(0).getText();
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
            throw new Error("More than one construct found");
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
        WhileStmtNode whileStmtNode=new WhileStmtNode()
    }

    @Override public ASTNode visitForStmt(MxParser.ForStmtContext ctx) {  

    @Override public ASTNode visitBreakStmt(MxParser.BreakStmtContext ctx) {  

    @Override public ASTNode visitContinueStmt(MxParser.ContinueStmtContext ctx) {  

    @Override public ASTNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {  

    @Override public ASTNode visitPureExprStmt(MxParser.PureExprStmtContext ctx) {

    }

    @Override public ASTNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
        return new EmptyStmtNode(new Position(ctx));
    }

    @Override public ASTNode visitNewVarExpr(MxParser.NewVarExprContext ctx) {  

    @Override public ASTNode visitFuncExpr(MxParser.FuncExprContext ctx) {  

    @Override public ASTNode visitArrayExpr(MxParser.ArrayExprContext ctx) {  

    @Override public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) {  

    @Override public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {  

    @Override public ASTNode visitPreExpr(MxParser.PreExprContext ctx) {  

    @Override public ASTNode visitSufExpr(MxParser.SufExprContext ctx) {  

    @Override public ASTNode visitParenExpr(MxParser.ParenExprContext ctx) {  

    @Override public ASTNode visitMemberfuncExpr(MxParser.MemberfuncExprContext ctx) {  

    @Override public ASTNode visitBasicExpr(MxParser.BasicExprContext ctx) {  

    @Override public ASTNode visitUnaryExpr(MxParser.UnaryExprContext ctx) {  

    @Override public ASTNode visitConditionExpr(MxParser.ConditionExprContext ctx) {  

    @Override public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {  

    @Override public ASTNode visitNewArrayExpr(MxParser.NewArrayExprContext ctx) {  

    @Override public ASTNode visitPrimary(MxParser.PrimaryContext ctx) {  

    @Override public ASTNode visitFstring(MxParser.FstringContext ctx) {  

    @Override public ASTNode visitConst(MxParser.ConstContext ctx) {  

    @Override public ASTNode visitConstArray(MxParser.ConstArrayContext ctx) {  
}
