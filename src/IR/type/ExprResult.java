package IR.type;

public class ExprResult {
    public String temp;//用于临时存储值的匿名变量 如%5
    public boolean isConst=false;//是否是常量，如果是常量的话，temp中记常量的值
    public String PtrName;//如果是左值,则记载其名字 如%a
    public boolean isPtr=false;
    public String PtrType;

    public ExprResult(){}

    public ExprResult(ExprResult obj) {
        this.temp=obj.temp;
        this.isConst=obj.isConst;
        this.PtrName=obj.PtrName;
        this.isPtr=obj.isPtr;
        this.PtrType=obj.PtrType;
    }
}
