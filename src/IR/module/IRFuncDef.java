package IR.module;

import IR.IRBlock;
import IR.type.IRType;

import java.util.ArrayList;

public class IRFuncDef {
    public String name;
    public IRType returntype;//返回类型
    public ArrayList<IRType> paramtypes;//参数类型
    public ArrayList<String> paramnames;//参数名
    public IRBlock entry;
    public ArrayList<IRBlock> body;

    public IRFuncDef(){
        this.entry.label="entry";
        body=new ArrayList<>();
        paramtypes=new ArrayList<>();
        paramnames=new ArrayList<>();
    }

    public String toString(){
        StringBuilder str=new StringBuilder();
        str.append("define ");
        str.append(returntype.toString());
        str.append(" @");
        str.append(name);
        str.append("(");
        for(int i=0; i<paramtypes.size(); i++){
            if(i>0){
                str.append(", ");
            }
            str.append(paramtypes.get(i).toString());
            str.append(" ");
            str.append(paramnames.get(i));
        }
        str.append(") {\n");
        str.append(entry.toString());
        for(int i=0; i<body.size(); i++){
            str.append(body.get(i).toString()).append("\n");
        }
        return str.toString();
    }
}
