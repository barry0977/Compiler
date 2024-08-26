package AST.Def;

import AST.ASTNode;
import AST.ASTVisitor;
import AST.Stmt.StmtNode;
import AST.Type.Type;
import Util.Position;
import Util.scope.Scope;
import Util.scope.funcScope;

import java.util.ArrayList;

public class FuncDefNode extends DefNode {
    public Type returntype;
    public ParalistNode paraslist;
    public ArrayList<StmtNode> body;
    public funcScope scope;//函数定义域
    public funcScope scope2;

    public FuncDefNode(Position pos) {
        super(pos);
        body = new ArrayList<>();
        paraslist = new ParalistNode(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
