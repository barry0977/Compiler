package IR.instr;

import IR.IRVisitor;

public class Load extends Instruction{
    public String result,type,pointer;

    public Load(){}

    public Load(String result, String type, String pointer){
        this.result = result;
        this.type = type;
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return "\t"+result+" = load "+type+", ptr "+pointer+";\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
