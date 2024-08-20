package IR.instr;

public class Ret extends Instruction{
    public String type;
    public String value;

    @Override
    public String toString() {
        if(type.equals("void")){
            return "ret void;\n";
        }else{
            return "ret "+type+" "+value+";\n";
        }
    }
}
