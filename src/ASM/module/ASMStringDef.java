package ASM.module;

public class ASMStringDef {
    public String name;
    public String value;
    public int length;

    public ASMStringDef(String name, String value,int length) {
        this.name = name;
        this.value = value;
        this.length = length-1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('@'+name+":\n");
        sb.append("\t.ascize \"");
        sb.append(value);
        sb.append("\"\n");
        sb.append("\t.size ");
        sb.append('@'+name);
        sb.append(", ");
        sb.append(length);
        sb.append("\n");
        return sb.toString();
    }
}
