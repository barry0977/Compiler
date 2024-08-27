package IR;

abstract public class IRNode {
    abstract public String toString();
    abstract public void accept(IRVisitor visitor);
}
