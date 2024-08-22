package IR.instr;

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
        String res=result+" = getelementptr "+type+", ptr "+pointer;
        for(int i=0;i<types.size();i++){
            res+=", "+types.get(i)+" "+idx.get(i);
        }
        res+=";\n";
        return res;
    }
}
