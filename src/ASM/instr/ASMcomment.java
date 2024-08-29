package ASM.instr;

public class ASMcomment extends ASMins{
    public String comment;
    public ASMcomment(String comment){
        this.comment = comment;
    }

    @Override
    public String toString(){
        return "# "+comment;
    }
}
