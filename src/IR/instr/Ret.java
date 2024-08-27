package IR.instr;

import IR.IRVisitor;

public class Ret extends Instruction{
    public String type;
    public String value;

    public Ret(){}

    public Ret(String type,String value){
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        if(type.equals("void")){
            return "\tret void;\n";
        }else{
            return "\tret "+type+" "+value+";\n";
        }
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
