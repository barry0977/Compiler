package IR.module;

import IR.IRNode;
import IR.IRVisitor;

import java.util.ArrayList;

public class IRFuncDecl extends IRNode {
    public String name;
    public String returntype;
    public ArrayList<String> paramtypes;

    public IRFuncDecl(String name,String returntype){
        this.name = name;
        this.returntype = returntype;
        this.paramtypes = new ArrayList<>();
    }

    @Override
    public String toString(){
        StringBuilder str=new StringBuilder();
        str.append("declare "+returntype+" @"+name+"(");
        for(int i=0; i<paramtypes.size(); i++){
            if(i>0) str.append(", ");
            str.append(paramtypes.get(i).toString());
        }
        str.append(")\n");
        return str.toString();
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
