package IR.instr;

import IR.IRVisitor;

import java.util.ArrayList;

public class Phi extends Instruction {
    public String result;
    public String ty;
    public ArrayList<String>vals;
    public ArrayList<String>labels;

    public Phi() {
        vals = new ArrayList<>();
        labels = new ArrayList<>();
    }

    public Phi(String result,String ty) {
        this.result = result;
        this.ty = ty;
        vals = new ArrayList<>();
        labels = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        sb.append(result);
        sb.append(" = phi ");
        sb.append(ty);
        sb.append(" ");
        for (int i = 0; i < labels.size(); i++) {
            if(i>0){
                sb.append(", ");
            }
            sb.append("[ ");
            sb.append(vals.get(i));
            sb.append(", %");
            sb.append(labels.get(i));
            sb.append(" ]");
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
