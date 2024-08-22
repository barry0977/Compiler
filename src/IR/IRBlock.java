package IR;

import IR.instr.Instruction;

import java.util.ArrayList;

public class IRBlock {
    public String label;
    public ArrayList<Instruction> statements;

    public IRBlock(String label) {
        this.label = label;
        statements = new ArrayList<>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(label).append(":\n");
        for (Instruction i : statements) {
            sb.append(i.toString()).append("\n");
        }
        return sb.toString();
    }
}
