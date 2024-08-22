package IR.instr;

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
        return "store "+type+" "+value+", ptr "+pointer+";\n";
    }
}
