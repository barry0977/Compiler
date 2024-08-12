package AST.Def;

import AST.ASTNode;
import AST.ASTVisitor;
import Util.Position;

import java.util.ArrayList;

public class ClassDefNode extends ASTNode {
    public String name;
    public ConstructNode construct;//构造函数，如果没有，则设置默认构造函数
    public ArrayList<VarDefNode> vars;
    public ArrayList<FuncDefNode> funcs;

    public ClassDefNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
