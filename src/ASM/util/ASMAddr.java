package ASM.util;

public class ASMAddr {
    public ASMRegister reg;
    public int offset;

    public ASMAddr(ASMRegister reg, int offset) {
        this.reg = reg;
        this.offset = offset;
    }

    public String toString() {
        return offset+"("+reg.toString()+")";
    }
}
