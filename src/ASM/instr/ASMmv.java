package ASM.instr;

import ASM.util.ASMRegister;

//把寄存器rs的值复制到寄存器rd中
//mv rd, rs
public class ASMmv extends ASMins {
    public ASMRegister rd,rs;
    public ASMmv(ASMRegister rd, ASMRegister rs) {
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public String toString(){
        return "mv "+rd+", "+rs;
    }
}
