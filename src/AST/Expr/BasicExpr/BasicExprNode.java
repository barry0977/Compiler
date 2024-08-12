package AST.Expr.BasicExpr;

import AST.ASTVisitor;
import AST.Expr.ExprNode;
import Util.Position;

public class BasicExprNode extends ExprNode {
    public boolean isThis = false,isTrue=false,isFalse=false;
    public boolean isInt=false,isString=false,isClass=false,isIdentifier=false;
    public String name;

    public BasicExprNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
