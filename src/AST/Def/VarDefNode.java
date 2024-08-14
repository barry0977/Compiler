package AST.Def;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Expr.ExprNode;
import AST.Type.Type;
import Util.Position;
import java.util.ArrayList;
import Util.Pair;

public class VarDefNode extends ASTNode {
    String name;
    public Type vartype;
    public ArrayList<Pair<String, ExprNode>> vars;

    public VarDefNode(Position pos) {
        super(pos);
        vars = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
