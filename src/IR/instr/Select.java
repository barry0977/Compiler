package IR.instr;

public class Select extends Instruction {
    public String result;
    public String cond;
    public String ty;
    public String val1,val2;

    public Select(){}

    public Select(String result, String cond, String ty, String val1, String val2) {
        this.result = result;
        this.cond = cond;
        this.ty = ty;
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public String toString() {
        return "\t"+result + " = select i1 " + cond + ", " + ty + " " + val1 + ", " + ty +" "+ val2+"\n";
    }
}
