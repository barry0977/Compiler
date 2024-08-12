package AST.Def;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Type.Type;
import Util.Pair;
import Util.Position;

import java.util.ArrayList;

public class ParalistNode extends ASTNode {
    public ArrayList<Pair<Type,String>> Paralist;

    public ParalistNode(Position pos){
        super(pos);
        Paralist = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
