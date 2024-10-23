package IR.instr;

import IR.IRVisitor;

import java.util.HashMap;

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
            case "+":
                op = "add";
                break;
            case "-":
                op = "sub";
                break;
            case "*":
                op = "mul";
                break;
            case "/":
                op = "sdiv";
                break;
            case "%":
                op = "srem";
                break;
            case "<<":
                op = "shl";
                break;
            case ">>" :
                op = "ashr";
                break;
            case "&":
                op = "and";
                break;
            case "|":
                op = "or";
                break;
            case "^":
                op = "xor";
                break;
        }
    }

    @Override
    public void rename(HashMap<String,String> map) {
        if(map.containsKey(lhs)){
            lhs = map.get(lhs);
        }
        if(map.containsKey(rhs)){
            rhs = map.get(rhs);
        }
    }

    @Override
    public void getUseDef(){
        def.add(result);
        addUse(lhs);
        addUse(rhs);
    }

    @Override
    public String toString(){
        return "\t"+result+" = "+op+" "+ty+" "+lhs+", "+rhs+"\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
