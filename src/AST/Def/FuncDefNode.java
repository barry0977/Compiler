package AST.Def;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Stmt.StmtNode;
import AST.Type.Type;
import Util.Position;

import java.util.ArrayList;

public class FuncDefNode extends ASTNode {
    public String funcname;
    public Type returntype;

    public ArrayList<StmtNode> body;

    public FuncDefNode(Position pos,Type returntype, ArrayList<StmtNode> body) {
        super(pos);
        this.returntype = returntype;
        this.body = body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
