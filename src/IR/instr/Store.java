package IR.instr;

import IR.IRVisitor;

public class Store extends Instruction{
    public String type,value,pointer;

    public Store(){}

    public Store(String type, String value, String pointer){
        this.type = type;
        this.value = value;
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return "\t"+"store "+type+" "+value+", ptr "+pointer+";\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
