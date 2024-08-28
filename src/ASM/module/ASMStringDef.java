package ASM.module;

public class ASMStringDef {
    public String name;
    public String value;

    public ASMStringDef(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name+":\n");
        sb.append("\t.ascize \"");
        sb.append(value);
        sb.append("\"\n");
        return sb.toString();
    }
}
