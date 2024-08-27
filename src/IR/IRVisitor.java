package IR;

import IR.instr.*;
import IR.module.*;

public interface IRVisitor {
    void visit(Alloca it);
    void visit(Binary it);
    void visit(Br it);
    void visit(Call it);
    void visit(Getelementptr it);
    void visit(Icmp it);
    void visit(Load it);
    void visit(Phi it);
    void visit(Ret it);
    void visit(Select it);
    void visit(Store it);

    void visit(IRClassDef it);
    void visit(IRFuncDecl it);
    void visit(IRFuncDef it);
    void visit(IRGlobalVarDef it);
    void visit(IRStringDef it);

    void visit(IRBlock it);
    void visit(IRProgram it);
}
