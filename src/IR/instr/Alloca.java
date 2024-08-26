package IR.instr;

public class Alloca extends Instruction{
    public String result,type;

    public Alloca(){}

    public Alloca(String result, String type){
        this.result = result;
        this.type = type;
    }

    @Override
    public String toString() {
        return "\t"+result+" = alloca "+type+";\n";
    }
}
