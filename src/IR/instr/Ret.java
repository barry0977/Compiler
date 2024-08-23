package IR.instr;

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
            return "ret void;\n";
        }else{
            return "ret "+type+" "+value+";\n";
        }
    }
}
