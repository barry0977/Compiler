package IR.instr;

public class Load extends Instruction{
    public String result,type,pointer;

    public Load(){}

    @Override
    public String toString() {
        return result+" = load "+type+", ptr "+pointer+";\n";
    }
}
