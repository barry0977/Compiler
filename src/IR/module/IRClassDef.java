package IR.module;

import IR.type.IRType;

import java.util.ArrayList;

public class IRClassDef {
    public String name;
    public ArrayList<IRType>members;

    public IRClassDef(){
        members = new ArrayList<>();
    }

    public String toString(){
        StringBuilder res=new StringBuilder();
        res.append("%class."+name+" = type {");
        for(int i=0; i<members.size(); i++){
            if(i>0){
                res.append(", ");
            }
            res.append(members.get(i).toString());
        }
        res.append(" }\n");
        return res.toString();
    }
}
