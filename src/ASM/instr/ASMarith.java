package ASM.instr;

import ASM.util.ASMRegister;

public class ASMarith extends ASMins{
    public String op;
    public ASMRegister rs1,rs2,rd;

    public ASMarith(String op, ASMRegister rs1, ASMRegister rs2, ASMRegister rd){
        this.op = op;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.rd = rd;
    }

    @Override
    public String toString(){
        return op+" "+rd.toString()+", "+rs1.toString()+", "+rs2.toString();
    }
}
