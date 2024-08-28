package ASM.instr;

import ASM.util.ASMRegister;

//用于beqz等
//beqz rs1, label
public class ASMbranch extends ASMins{
    public ASMRegister rs1;
    public String label;
    public String cond;

    public ASMbranch(ASMRegister rs1,String label,String cond){
        this.rs1 = rs1;
        this.label = label;
        this.cond = cond;
    }

    @Override
    public String toString(){
        return cond+" "+rs1.toString()+", "+label;
    }
}
