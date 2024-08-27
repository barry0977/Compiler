package IR.module;

import IR.IRNode;
import IR.IRVisitor;
import IR.type.IRType;

import java.util.ArrayList;

public class IRClassDef extends IRNode {
    public String name;
    public ArrayList<IRType>members;

    public IRClassDef(){
        members = new ArrayList<>();
    }

    @Override
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

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
