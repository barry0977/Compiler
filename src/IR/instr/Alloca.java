package IR.instr;

public class Alloca extends Instruction{
    public String result,type;

    @Override
    public String toString() {
        return result+" = alloca "+type+";\n";
    }
}
