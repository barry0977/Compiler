import AST.ProgramNode;
import Frontend.ASTBuilder;
import Frontend.IRBuilder;
import Frontend.SemanticChecker;
import Frontend.SymbolCollector;
import IR.IRProgram;
import Parser.MxLexer;
import Parser.MxParser;
import Util.MxErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import Util.scope.*;
import Util.error.Error;

public class Main {
    public static void main(String[] args) throws Exception{
        var input = CharStreams.fromStream(System.in);
        try {
            ProgramNode ASTRoot;
            globalScope gScope = new globalScope(null);
            globalScope gScope2 = new globalScope(null);//作为gScope的备份，存储symbolcollector之后的gScope，用在IRBuilder中

            MxLexer lexer = new MxLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree parseTreeRoot = parser.program();
            ASTBuilder astBuilder = new ASTBuilder();
            ASTRoot=(ProgramNode) astBuilder.visit(parseTreeRoot);
            new SymbolCollector(gScope,gScope2).visit(ASTRoot);
            new SemanticChecker(gScope).visit(ASTRoot);
            //IR
            IRProgram irprogram = new IRProgram();
            new IRBuilder(irprogram,gScope2).visit(ASTRoot);

        } catch (Error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }
    }
}