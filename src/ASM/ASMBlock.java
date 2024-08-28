package ASM;

import ASM.instr.ASMins;

import java.util.ArrayList;

public class ASMBlock {
    public String label;
    public ArrayList<ASMins>body;

    public ASMBlock(String label){
        this.label = label;
        body = new ArrayList<>();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(label);
        sb.append(":\n");
        for(ASMins ins : body){
            sb.append("\t");
            sb.append(ins.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
