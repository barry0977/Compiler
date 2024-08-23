package IR.instr;

public class Icmp extends Instruction{
    public String result;
    public String cond;
    public String ty;
    public String op1,op2;

    public Icmp(){}

    public Icmp(String result, String cond, String ty, String op1, String op2){
        this.result = result;
        this.cond = getOp(cond);
        this.ty = ty;
        this.op1 = op1;
        this.op2 = op2;
    }

    public String getOp(String op){
        String res=null;
        switch (op){
            case ">" :
                res = "sgt";
                break;
            case ">=" :
                res = "sge";
                break;
            case "<" :
                res = "slt";
                break;
            case "<=" :
                res = "sle";
                break;
            case "==" :
                res = "eq";
                break;
            case "!=" :
                res = "ne";
                break;
        }
        return res;
    }

    @Override
    public String toString(){
        return result+" = icmp "+cond+" "+ty+" "+op1+", "+op2+";\n";
    }
}
