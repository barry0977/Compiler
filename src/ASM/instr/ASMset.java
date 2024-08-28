package ASM.instr;

import ASM.util.ASMRegister;

//用于seqz,snez,sltz,sgtz
//op rd,rs1
public class ASMset extends ASMins{
    public ASMRegister rd,rs1;
    public String op;

    public ASMset(ASMRegister rd,ASMRegister rs1,String op){
        this.rd = rd;
        this.rs1 = rs1;
        this.op = op;
    }

    @Override
    public String toString(){
        return op+" "+rd.toString()+", "+rs1.toString();
    }
}
