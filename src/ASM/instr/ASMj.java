package ASM.instr;

//j offset
public class ASMj extends ASMins{
    public String label;

    public ASMj(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return "j "+label;
    }
}
