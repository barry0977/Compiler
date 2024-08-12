package AST.Expr.BasicExpr;

import AST.ASTVisitor;
import AST.Type.Type;
import Util.Position;

public class StringConstNode extends BasicExprNode {
    public String value;

    public StringConstNode(Position pos){
        super(pos);
        type=new Type("string",0);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
