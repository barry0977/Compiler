package IR.instr;

public class Binary extends Instruction {
    public String op;
    public String lhs,rhs;
    public String result;
    public Boolean isInt=false,isBool=false;

    public Binary(){}

    public Binary(String lhs, String rhs, String result) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.result = result;
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
        if(isInt){
            return result+" = "+op+" i32 "+lhs+", "+rhs+"\n";
        }else{
            return result+" = "+op+" i1 "+lhs+", "+rhs+"\n";
        }
    }
}
