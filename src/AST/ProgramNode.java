package AST;

import AST.Def.ClassDefNode;
import AST.Def.DefNode;
import AST.Def.FuncDefNode;
import AST.Def.VarDefNode;
import Util.Position;

import java.util.ArrayList;

public class ProgramNode extends ASTNode{
    public ArrayList<DefNode>defNodes;//应该都用基类DefNode来构建一个列表，否则顺序会出问题

    public ProgramNode(Position pos){
        super(pos);
        defNodes = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}

