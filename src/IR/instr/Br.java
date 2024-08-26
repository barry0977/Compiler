package IR.instr;

public class Br extends Instruction {
    public String cond,iftrue,iffalse;
    public String dest;
    public boolean haveCondition=false;//是否是条件分支

    public Br() {}

    public Br(String cond, String iftrue, String iffalse) {
        this.cond = cond;
        this.iftrue = iftrue;
        this.iffalse = iffalse;
        this.haveCondition=true;
    }

    public Br(String dest){
        this.dest = dest;
        this.haveCondition=false;
    }

    @Override
    public String toString(){
        if(haveCondition){
            return "\t"+"br i1 "+cond+", label %"+iftrue+",label %"+iffalse+";\n";
        }else{
            return "\t"+"br label %"+dest+";\n";
        }
    }
}