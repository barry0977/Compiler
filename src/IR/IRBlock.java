package IR;

import IR.instr.*;

import java.util.ArrayList;

public class IRBlock {
    public String label;
    public ArrayList<Instruction> statements;
    public Instruction terminalStmt=null;

    public IRBlock(String label) {
        this.label = label;
        statements = new ArrayList<>();
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
            else if(terminalStmt!=null){
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
}
