import AST.ProgramNode;
import Frontend.ASTBuilder;
import Frontend.SemanticChecker;
import Frontend.SymbolCollector;
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
        System.err.println("hello world");
        try {
            ProgramNode ASTRoot;
            globalScope gScope = new globalScope(null);

            MxLexer lexer = new MxLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree parseTreeRoot = parser.program();
            ASTBuilder astBuilder = new ASTBuilder();
            ASTRoot=(ProgramNode) astBuilder.visit(parseTreeRoot);
            new SymbolCollector(gScope).visit(ASTRoot);
            new SemanticChecker(gScope).visit(ASTRoot);
        } catch (Error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }
    }
}