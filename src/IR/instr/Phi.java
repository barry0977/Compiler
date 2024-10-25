package IR.instr;

import IR.IRVisitor;

import java.util.ArrayList;
import java.util.HashMap;

public class Phi extends Instruction {
    public String result;
    public String ty;
    public ArrayList<String>vals;
    public ArrayList<String>labels;
    public HashMap<String,Integer>label_order;

    public Phi() {
        vals = new ArrayList<>();
        labels = new ArrayList<>();
        label_order = new HashMap<>();
    }

    public Phi(String result,String ty) {
        this.result = result;
        this.ty = ty;
        vals = new ArrayList<>();
        labels = new ArrayList<>();
        label_order = new HashMap<>();
    }

    public void changeBlock(String oldBlock, String newBlock) {
        for(int i=0;i<labels.size();i++) {
            if(labels.get(i).equals(oldBlock)) {
                labels.set(i,newBlock);
                label_order.put(newBlock,label_order.get(oldBlock));
                label_order.remove(oldBlock);
            }
        }
    }

    @Override
    public void rename(HashMap<String, String> map){
        for(int i=0;i<vals.size();i++){
            if(map.containsKey(vals.get(i))){
                vals.set(i,map.get(vals.get(i)));
            }
        }
    }

    @Override
    public void getUseDef(){
        def.add(result);
        for(var value:vals){
            addUse(value);
        }
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
