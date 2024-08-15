package AST.Def;

import AST.ASTNode;
import AST.ASTVisitor;
import Util.Position;
import Util.scope.Scope;
import Util.scope.classScope;

import java.util.ArrayList;

public class ClassDefNode extends DefNode {
    public ConstructNode construct;//构造函数，如果没有，则设置默认构造函数
    public ArrayList<VarDefNode> vars;
    public ArrayList<FuncDefNode> funcs;
    public classScope scope;

    public ClassDefNode(Position pos) {
        super(pos);
        vars = new ArrayList<>();
        funcs = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
