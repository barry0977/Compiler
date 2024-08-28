package ASM.instr;

public class ASMcall extends ASMins{
    public String label;

    public ASMcall(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return "call "+label;
    }
}
