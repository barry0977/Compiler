package ASM.instr;

import ASM.util.ASMRegister;

//把imm存储到寄存器rd中
//li rd,imm
public class ASMli extends ASMins{
    public ASMRegister rd;
    public int imm;

    public ASMli(ASMRegister rd, int imm){
        this.rd = rd;
        this.imm = imm;
    }

    @Override
    public String toString(){
        return "li "+rd.toString()+", "+imm;
    }
}
