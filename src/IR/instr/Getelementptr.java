package IR.instr;

import IR.IRVisitor;

import java.util.ArrayList;

public class Getelementptr extends Instruction{
    public String result,type,pointer;
    public ArrayList<String>types;
    public ArrayList<String>idx;

    public Getelementptr(){
        types=new ArrayList<>();
        idx=new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("\t");
        sb.append(result+" = getelementptr "+type+", ptr "+pointer);
        for(int i=0;i<types.size();i++){
            sb.append(", "+types.get(i)+" "+idx.get(i));
        }
        sb.append(";\n");
        return sb.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
