package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

import java.util.ArrayList;

public class FStringExprNode extends ExprNode {
    public ArrayList<String> stringlist;
    public ArrayList<ExprNode> exprlist;

    public FStringExprNode(Position pos) {
        super(pos);
        stringlist = new ArrayList<>();
        exprlist = new ArrayList<>();
    }

    public String GetString(String str) {
        String res=str.substring(2, str.length() - 1);
        res.replace("\\\\","\\").replace("\\n","\n").replace("\\\"","\"");
        return res;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
