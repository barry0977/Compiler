package IR.instr;

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
        sb.append(result);
        sb.append(" = phi ");
        sb.append(ty);
        sb.append(" ");
        for (int i = 0; i < vals.size(); i++) {
            if(i>0){
                sb.append(", ");
            }
            sb.append("[ ");
            sb.append(vals.get(i));
            sb.append(", ");
            sb.append(labels.get(i));
            sb.append(" ]");
        }
        return sb.toString();
    }
}
