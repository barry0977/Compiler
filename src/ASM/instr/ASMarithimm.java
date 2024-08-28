package ASM.instr;

import ASM.util.ASMRegister;

public class ASMarithimm {
    public String op;
    public ASMRegister rs1,rd;
    public int imm;

    public ASMarithimm(String op, ASMRegister rs1, ASMRegister rd, int imm) {
        this.op = op;
        this.rs1 = rs1;
        this.rd = rd;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return op+" "+rd.toString()+", "+rs1.toString()+", "+imm;
    }
}
