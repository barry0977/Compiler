package IR.instr;

public class Icmp extends Instruction{
    public String result;
    public String cond;
    public String ty;
    public String op1,op2;

    public Icmp(){}

    public Icmp(String result, String cond, String ty, String op1, String op2){
        this.result = result;
        this.cond = cond;
        this.ty = ty;
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public String toString(){
        return result+" = icmp "+cond+" "+ty+" "+op1+", "+op2+";\n";
    }
}
