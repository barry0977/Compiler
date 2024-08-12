package Frontend;

import Parser.MxBaseVisitor;
import Parser.MxParser;
import Parser.MxLexer;
import AST.ASTNode;

public class ASTBuilder extends MxBaseVisitor<ASTNode> {
    @Override public ASTNode visitProgram(MxParser.ProgramContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitSuite(MxParser.SuiteContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitBasictype(MxParser.BasictypeContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitTypename(MxParser.TypenameContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitType(MxParser.TypeContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitReturntype(MxParser.ReturntypeContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitVarDef(MxParser.VarDefContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitFuncDef(MxParser.FuncDefContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitConstruct(MxParser.ConstructContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitClassDef(MxParser.ClassDefContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitBlock(MxParser.BlockContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitVardefStmt(MxParser.VardefStmtContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitIfStmt(MxParser.IfStmtContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitForStmt(MxParser.ForStmtContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitBreakStmt(MxParser.BreakStmtContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitContinueStmt(MxParser.ContinueStmtContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitReturnStmt(MxParser.ReturnStmtContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitPureExprStmt(MxParser.PureExprStmtContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitNewVarExpr(MxParser.NewVarExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitFuncExpr(MxParser.FuncExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitArrayExpr(MxParser.ArrayExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitPreExpr(MxParser.PreExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitSufExpr(MxParser.SufExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitParenExpr(MxParser.ParenExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitMemberfuncExpr(MxParser.MemberfuncExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitBasicExpr(MxParser.BasicExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitUnaryExpr(MxParser.UnaryExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitConditionExpr(MxParser.ConditionExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitNewArrayExpr(MxParser.NewArrayExprContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitPrimary(MxParser.PrimaryContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitFstring(MxParser.FstringContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitConst(MxParser.ConstContext ctx) { return visitChildren(ctx); }

    @Override public ASTNode visitConstArray(MxParser.ConstArrayContext ctx) { return visitChildren(ctx); }
}
