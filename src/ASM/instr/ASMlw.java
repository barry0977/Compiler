package ASM.instr;

import ASM.util.ASMAddr;
import ASM.util.ASMRegister;

//将寄存器rs1加上offset内存位置处的数据加载到寄存器rd中
//lw rd offset(rs1)
public class ASMlw extends ASMins{
    public ASMRegister rd;
    public ASMAddr addr;

    public ASMlw(ASMRegister rd, ASMAddr addr) {
        this.rd = rd;
        this.addr = addr;
    }

    @Override
    public String toString(){
        return "lw "+rd.toString()+", "+addr.toString();
    }
}
