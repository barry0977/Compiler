package IR.type;

//保存每次运算后的结果，如果是右值，则只有temp（即寄存器）
//如果是左值，则会保存在PtrName，并通过Load指令存到一个寄存器中，保存在temp
//左值的指针只会在load和store用到
public class ExprResult {
    public String temp;//用于临时存储值的匿名变量 如%5
    public boolean isConst=false;//是否是常量，如果是常量的话，temp中记常量的值
    public String PtrName;//如果是左值,则记载其指针 如%a
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
