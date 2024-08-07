package AST;

import org.antlr.runtime.tree.TreeWizard;

abstract public class ASTNode {
    public position pos;
    public ASTnode(position pos){
        this.pos = pos;
    }

    abstract public void accept(ASTVisitor visitor);
}
