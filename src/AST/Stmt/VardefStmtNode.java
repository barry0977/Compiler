package AST.Stmt;

import AST.ASTVisitor;
import AST.Def.VarDefNode;
import Util.Position;

import java.util.ArrayList;

public class VardefStmtNode extends StmtNode {
    public VarDefNode varDef=null;

    public VardefStmtNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
