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
        sb.append("\t.p2align 2\n");
        sb.append('@'+name+":\n");
        if(value.equals("null")){
            sb.append("\t.word ");
            sb.append("0");
        }else{
            sb.append("\t.word ");
            sb.append(value);
        }
        sb.append("\n");
        return sb.toString();
    }
}
