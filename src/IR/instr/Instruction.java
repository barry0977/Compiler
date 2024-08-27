package IR.instr;

import IR.IRNode;
import IR.IRVisitor;

abstract public class Instruction extends IRNode {
    public Instruction() {}

    @Override
    abstract public String toString();

    @Override
    abstract public void accept(IRVisitor visitor);
}
