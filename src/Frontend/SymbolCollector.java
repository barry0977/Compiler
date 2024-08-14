package Frontend;

import AST.ASTVisitor;
import AST.ProgramNode;
import Util.Decl.ClassDecl;
import Util.Decl.FuncDecl;
import Util.scope.globalScope;

public class SymbolCollector implements ASTVisitor {
    private globalScope gScope;

    public SymbolCollector(globalScope gScope) {
        this.gScope = gScope;
    }

    @Override
    public void visit(ProgramNode node){
        for(var func:node.funcNodes){
            gScope.funcDecls.put(func.funcname,new FuncDecl(func));
        }
        for(var clas:node.classNodes){
            gScope.classDecls.put(clas.name,new ClassDecl(clas));
        }
        for(var x:node.varNodes){
            gScope.vars.put(x.vars.getFirst(),x.vartype);
        }
    }
}
