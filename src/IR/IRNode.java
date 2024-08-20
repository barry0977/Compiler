package IR;

import AST.ASTVisitor;

abstract public class IRNode {
    abstract public String toString();
    abstract public void accept(ASTVisitor visitor);
}
