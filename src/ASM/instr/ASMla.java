package ASM.instr;

import ASM.util.ASMRegister;

//把label的地址存到寄存器rd中
//la rd,label
public class ASMla extends ASMins{
    public ASMRegister rd;
    public String label;

    public ASMla(ASMRegister rd, String label) {
        this.rd = rd;
        this.label = label;
    }

    @Override
    public String toString(){
        return "la "+rd.toString()+", "+label;
    }
}
