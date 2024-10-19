package IR.instr;

import IR.IRVisitor;

import java.util.ArrayList;
import java.util.HashMap;

public class Getelementptr extends Instruction{
    public String result,type,pointer;
    public ArrayList<String>types;
    public ArrayList<String>idx;

    public Getelementptr(){
        types=new ArrayList<>();
        idx=new ArrayList<>();
    }

    @Override
    public void rename(HashMap<String, String> map){
        if(map.containsKey(pointer)){
            pointer=map.get(pointer);
        }
        for(int i=0;i<types.size();i++){
            if(map.containsKey(idx.get(i))){
                idx.set(i,map.get(idx.get(i)));
            }
        }
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
