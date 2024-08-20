package IR.instr;

public class Br extends Instruction {
    public String cond,iftrue,iffalse;
    public String dest;
    public boolean haveCondition=false;//是否是条件分支

    @Override
    public String toString(){
        if(haveCondition){
            return "br i1 "+cond+", label "+iftrue+",label "+iffalse+";\n";
        }else{
            return "br label "+dest+";\n";
        }
    }
}