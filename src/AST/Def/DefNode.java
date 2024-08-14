package AST.Def;

import AST.ASTNode;
import AST.ASTVisitor;
import Util.Position;

abstract public class DefNode extends ASTNode {
    public String name;

    public DefNode(Position pos){
        super(pos);
    }

    @Override
    abstract public void accept(ASTVisitor visitor);
}
