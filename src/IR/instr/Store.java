package IR.instr;

public class Store extends Instruction{
    public String type,value,pointer;

    @Override
    public String toString() {
        return "store "+type+" "+value+", ptr "+pointer+";\n";
    }
}
