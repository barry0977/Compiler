package AST.Def;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Expr.ExprNode;
import AST.Type.Type;
import Util.Position;

public class VarDefNode extends ASTNode {
    public String name;
    public Type type;
    public ExprNode value;

    public VarDefNode(String name, Type type, ExprNode value, Position pos) {
        super(pos);
        this.name = name;
        this.type = type;
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
