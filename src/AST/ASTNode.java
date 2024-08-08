package AST;

import Util.Position;
import org.antlr.runtime.tree.TreeWizard;

abstract public class ASTNode {
    public Position pos;
    public ASTNode(Position pos){
        this.pos = pos;
    }

    abstract public void accept(ASTVisitor visitor);
}
