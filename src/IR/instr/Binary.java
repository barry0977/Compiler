package IR.instr;

public class Binary extends Instruction {
    public String op;
    public String lhs,rhs;
    public String result;
    public String ty;

    public Binary(){}

    public Binary(String opCode,String ty,String lhs, String rhs, String result) {
        this.ty=ty;
        this.lhs = lhs;
        this.rhs = rhs;
        this.result = result;
        setOp(opCode);
    }

    public void setOp(String obj){
        switch (obj) {
            case "+" -> op = "add";
            case "-" -> op = "sub";
            case "*" -> op = "mul";
            case "/" -> op = "sdiv";
            case "%" -> op = "srem";
            case "<<" -> op = "shl";
            case ">>" -> op = "ashr";
            case "&" -> op = "and";
            case "|" -> op = "or";
            case "^" -> op = "xor";
        }
    }

    @Override
    public String toString(){
        return result+" = "+op+" "+ty+" "+lhs+", "+rhs+"\n";
    }
}
