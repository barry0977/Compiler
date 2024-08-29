package IR.module;

import IR.IRVisitor;

public class IRStringDef extends IRGlobalVarDef{
    public String label;
    public int length;
    public String value;
    public String origin;//原始字符串

    public IRStringDef(String ori){
        value=ori+"\\00";
        origin=ori;
        length=ori.length()+1;
    }

    public IRStringDef(int _label,String ori,boolean isfstr) {
        this.label = _label+"";
        this.origin=ori;
        if(isfstr){
            setValueFormat(ori);
        }else{
            setValue(ori);
        }
    }

    public void setValue(String tmp) {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<tmp.length();i++) {
            char c = tmp.charAt(i);
            if(c=='\\'){
                char next = tmp.charAt(i+1);
                if(next=='\\'){
                    sb.append("\\\\");
                }else if(next=='n'){
                    sb.append("\\0A");
                }else if(next=='"'){
                    sb.append("\\22");
                }
                length++;
                i++;
                continue;
            }
            sb.append(c);
            length++;
        }
        sb.append("\\00");
        length++;
        this.value=sb.toString();
    }

    public void setValueFormat(String tmp) {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<tmp.length();i++) {
            char c = tmp.charAt(i);
            if(c=='\\'){
                char next = tmp.charAt(i+1);
                if(next=='\\'){
                    sb.append("\\\\");
                }else if(next=='n'){
                    sb.append("\\0A");
                }else if(next=='"'){
                    sb.append("\\22");
                }
                length++;
                i++;
                continue;
            }
            if(c=='$'){//格式化字符串中用$$替代$
                sb.append("$");
                i++;
                continue;
            }
            sb.append(c);
            length++;
        }
        sb.append("\\00");
        length++;
        this.value=sb.toString();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("@.str."+label);
        sb.append(" = private unnamed_addr constant [");
        sb.append(length+" x i8] c\"");
        sb.append(value);
        sb.append("\"\n");
        return sb.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
