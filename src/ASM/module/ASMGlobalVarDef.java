package ASM.module;

public class ASMGlobalVarDef {
    public String name;
    public String value;

    public ASMGlobalVarDef(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t.globl ");
        sb.append('@'+name);
        sb.append("\n");
        sb.append('@'+name+":\n");
        sb.append("\t.zero ");
        sb.append(value);
        sb.append("\n");
        return sb.toString();
    }
}
