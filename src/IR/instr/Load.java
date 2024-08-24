package IR.instr;

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
        return result+" = load "+type+", ptr "+pointer+";\n";
    }
}
