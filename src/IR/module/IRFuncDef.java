package IR.module;

import IR.IRBlock;
import IR.IRNode;
import IR.IRVisitor;
import IR.type.IRType;

import java.util.ArrayList;

public class IRFuncDef extends IRNode {
    public String name;
    public IRType returntype;//返回类型
    public ArrayList<IRType> paramtypes;//参数类型
    public ArrayList<String> paramnames;//参数名
    public IRBlock entry;
    public int cnt=0;//用于一个函数中的匿名函数的命名
    public int shortname=0;//用于块编号
    public ArrayList<IRBlock> body;

    public IRFuncDef(){
        entry=new IRBlock("entry");
        body=new ArrayList<>();
        paramtypes=new ArrayList<>();
        paramnames=new ArrayList<>();
    }

    public IRFuncDef(String name,String type){
        entry=new IRBlock("entry");
        this.name=name;
        this.returntype=new IRType(type);
        body=new ArrayList<>();
        paramtypes=new ArrayList<>();
        paramnames=new ArrayList<>();
    }

    public IRBlock addBlock(IRBlock block){
        body.add(block);
        return block;
    }

    @Override
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
            str.append(body.get(i).toString());
        }
        str.append("}\n\n");
        return str.toString();
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
