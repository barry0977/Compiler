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
        String res="%class."+name+" = type {";
        for(int i=0; i<members.size()-1; i++){
            res += members.get(i).toString();
            res+=", ";
        }
        res+=members.get(members.size()-1).toString()+" }";
        return res;
    }
}
