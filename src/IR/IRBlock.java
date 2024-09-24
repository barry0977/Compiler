package IR;

import IR.instr.*;

import java.util.ArrayList;
import java.util.HashMap;

public class IRBlock extends IRNode {
    public String label;
    public ArrayList<Instruction> statements;
    public Instruction terminalStmt=null;
    public HashMap<String,String>def2use;//块内def和use的映射,保存最近一次指针被def的值

    public IRBlock(String label) {
        this.label = label;
        statements = new ArrayList<>();
        def2use = new HashMap<>();
    }

    public void addIns(Instruction ins) {
        if(label.equals("entry")){
            if(terminalStmt == null){
                if((ins instanceof Br)||(ins instanceof Ret)){
                    terminalStmt=ins;
                }else{
                    statements.add(ins);
                }
            }
            else{
                if(!(ins instanceof Br||ins instanceof Ret)){
                    statements.add(ins);
                }
            }
        }else{
            if(terminalStmt==null){
                if((ins instanceof Br)||(ins instanceof Ret)){
                    terminalStmt=ins;
                }else{
                    statements.add(ins);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(label).append(":\n");
        for (Instruction i : statements) {
            sb.append(i.toString());
        }
        if(terminalStmt!=null){
            sb.append(terminalStmt.toString());
        }
        return sb.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
