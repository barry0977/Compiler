package AST;

import AST.Def.ClassDefNode;
import AST.Def.FuncDefNode;
import AST.Def.VarDefNode;
import Util.Position;

import java.util.ArrayList;

public class ProgramNode extends ASTNode{
    public ArrayList<VarDefNode>varNodes;
    public ArrayList<FuncDefNode>funcNodes;
    public ArrayList<ClassDefNode>classNodes;

    public ProgramNode(Position pos){
        super(pos);
        varNodes = new ArrayList<>();
        funcNodes = new ArrayList<>();
        classNodes = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}

