package AST.Def;

import AST.ASTNode;
import AST.Stmt.StmtNode;
import Util.Position;

import java.util.ArrayList;

//类的构造函数
public class ConstructNode extends ASTNode {
    public String name;
    public ArrayList<StmtNode> stmts;

    public ConstructNode(Position pos) {
        super(pos);
        stmts = new ArrayList<>();
    }

    @Override
    public
}
