package IR.instr;

import IR.IRVisitor;

import java.util.HashMap;

public class Alloca extends Instruction{
    public String result,type;

    public Alloca(){}

    public Alloca(String result, String type){
        this.result = result;
        this.type = type;
    }

    @Override
    public void rename(HashMap<String, String> map){
    }

    @Override
    public void getUseDef(){
        def.add(result);
    }

    @Override
    public String toString() {
        return "\t"+result+" = alloca "+type+";\n";
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
