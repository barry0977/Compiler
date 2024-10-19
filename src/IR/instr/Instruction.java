package IR.instr;

import IR.IRNode;
import IR.IRVisitor;

import java.util.HashMap;

abstract public class Instruction extends IRNode {
    public Instruction() {}

    abstract public void rename(HashMap<String, String> map);

    @Override
    abstract public String toString();

    @Override
    abstract public void accept(IRVisitor visitor);
}
