package ASM.instr;

import ASM.util.ASMAddr;
import ASM.util.ASMRegister;

//将寄存器rd的数据存储到寄存器rs1的值加上offset的内存位置中
//sw rd offset(rs1)
public class ASMsw extends ASMins{
    public ASMRegister rd;
    public ASMAddr addr;

    public ASMsw(ASMRegister rd,ASMAddr addr){
        this.rd = rd;
        this.addr = addr;
    }

    @Override
    public String toString(){
        return "sw "+rd.toString()+", "+addr.toString();
    }
}
