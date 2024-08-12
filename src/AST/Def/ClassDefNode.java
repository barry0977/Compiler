package AST.Def;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Type.Type;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;

public class ClassDefNode extends ASTNode {
    public String name;


    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
