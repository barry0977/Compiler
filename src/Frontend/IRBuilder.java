package Frontend;

import AST.ASTVisitor;
import AST.Def.*;
import AST.Expr.*;
import AST.Expr.BasicExpr.ArrayConstNode;
import AST.Expr.BasicExpr.BasicExprNode;
import AST.ProgramNode;
import AST.Stmt.*;
import IR.IRBlock;
import IR.instr.*;
import IR.module.*;
import IR.IRProgram;
import IR.type.ExprResult;
import IR.type.IRType;
import Util.scope.*;

public class IRBuilder implements ASTVisitor {
    IRProgram program;
    globalScope gScope;
    Scope curScope;
    IRBlock curBlock;
    ExprResult lastExpr;//最后一次运算出的结果
    IRFuncDef curFunc;

    public IRBuilder(IRProgram program, globalScope gscope) {
        this.program = program;
        this.gScope = gscope;
        this.curScope = gscope;
    }

    public void visit(ProgramNode it){
        for(var def:it.defNodes){
            def.accept(this);
        }
    }

    public void visit(ClassDefNode it){
        curScope=it.scope;
        IRClassDef classDef = new IRClassDef();
        classDef.name=it.name;
        for(var mem:it.vars){
            classDef.members.add(new IRType(mem.vartype));
        }
        for(var func:it.funcs){
            func.accept(this);
        }
        curScope=curScope.parent;
    }

    public void visit(FuncDefNode it){
        curScope=it.scope;
        IRFuncDef funcDef=new IRFuncDef();
        if(curScope instanceof globalScope){//全局函数
            funcDef.name=it.name;
        }else{//类方法，名字修改，参数要加上this
            funcDef.name=((classScope) curScope.parent).classname+"::"+it.name;//名字前加上类名
            funcDef.paramnames.add("%this");
            funcDef.paramtypes.add(new IRType("ptr"));
            Alloca ins1=new Alloca("%this.addr","ptr");
            funcDef.entry.addIns(ins1);
            Store ins2=new Store("ptr","%this","%this.addr");
            funcDef.entry.addIns(ins2);
        }
        for(var args:it.paraslist.Paralist){//加入参数列表
            funcDef.paramnames.add("%"+args.second);
            funcDef.paramtypes.add(new IRType(args.first));
            Alloca ins1=new Alloca();
            ins1.result="%"+args.second+".addr";//参数指针名字加上addr
            ins1.type=new IRType(args.first).toString();//这里alloca的类应该都是ptr
            funcDef.entry.addIns(ins1);
            Store ins2=new Store();
            ins2.type=new IRType(args.first).toString();
            ins2.value="%"+args.second;
            ins2.pointer="%"+args.second+".addr";
            funcDef.entry.addIns(ins2);
        }
        funcDef.returntype=new IRType(it.returntype);
        this.curFunc=funcDef;
        for(var stmt:it.body){
            stmt.accept(this);
        }
        curScope=curScope.parent;
    }

    public void visit(VarDefNode it){
        if(curScope instanceof globalScope){//全局变量
            for(var variable:it.vars){
                if(variable.second==null){//没有初始值
                    IRGlobalVarDef gvar=new IRGlobalVarDef(variable.first,new IRType(it.vartype),"null");
                    program.globalvars.put(variable.first,gvar);
                    continue;
                }else{
                    if(variable.second instanceof BasicExprNode && (((BasicExprNode) variable.second).isInt||((BasicExprNode) variable.second).isFalse||((BasicExprNode) variable.second).isTrue||((BasicExprNode) variable.second).isNull)){
                        IRGlobalVarDef gvar=new IRGlobalVarDef(variable.first,new IRType(it.vartype),((BasicExprNode) variable.second).value);
                        program.globalvars.put(variable.first,gvar);
                        continue;
                    }else{
                        IRGlobalVarDef gvar=new IRGlobalVarDef(variable.first,null,"null");
                        program.globalvars.put(variable.first,gvar);
                        if(!program.funcs.containsKey("_init")){
                            program.funcs.put("_init",new IRFuncDef("_init","void"));
                        }
                        var tmp=program.funcs.get("_init");
                        variable.second.accept(this);
                        //TODO
                        //在init函数中初始化
                    }
                }
            }
        }else{//局部变量
            for(var variable:it.vars){
                Alloca ins=new Alloca();
                ins.result="%"+variable.first+"."+curScope.depth+"."+curScope.order;
                ins.type=new IRType(it.vartype).toString();
                if(variable.second!=null){
                    variable.second.accept(this);
                    //TODO
                }
            }
        }
    }

    public void visit(ConstructNode it){
    }

    public void visit(ParalistNode it){
    }

    public void visit(IfStmtNode it){
        it.condition.accept(this);

    }

    public void visit(WhileStmtNode it){
    }

    public void visit(ForStmtNode it){
    }

    public void visit(BreakStmtNode it){

    }

    public void visit(ContinueStmtNode it){

    }

    public void visit(PureExprStmtNode it){
        it.expr.accept(this);
    }

    public void visit(ReturnStmtNode it){
        if(it.returnExpr!=null){
            it.returnExpr.accept(this);
            String type=new IRType(it.returnExpr.type).toString();
            curBlock.addIns(new Ret(type,lastExpr.temp));
        }else{
            curBlock.addIns(new Ret("void",null));
        }
    }

    public void visit(BlockStmtNode it){

    }

    public void visit(VardefStmtNode it){
        it.varDef.accept(this);
    }

    public void visit(EmptyStmtNode it){
        return;
    }

    public void visit(ArrayExprNode it){
    }

    public void visit(AssignExprNode it){
    }

    public void visit(BasicExprNode it){
    }

    public void visit(BinaryExprNode it){
        it.lhs.accept(this);//左操作数必须要执行
        ExprResult lhsvalue=lastExpr;
        if(it.opCode.equals("&&")||it.opCode.equals("||")){
            int ord=curScope.cnt++;//每次需要短路求值就增加这个cnt，防止block名字重复
            int suf=curFunc.cnt++;//同一个函数中匿名变量不能重复
            curBlock.addIns(new Icmp("%"+suf,"ne","i1",lhsvalue.temp,"0"));//若为true，则说明左边为true
            if(it.opCode.equals("&&")){//若左边是false，则不用看右边
                curBlock.addIns(new Br("%"+suf,"logic.rhs."+ord,"logic.end."+ord));
            }else{//若左边是true，则不用看右边
                curBlock.addIns(new Br("%"+suf,"logic.end."+ord,"logic.rhs."+ord));
            }
            String ori=curBlock.label;//最开始块的label

            curBlock=curFunc.addBlock(new IRBlock("logic.rhs."+ord));//还需要执行右操作数
            it.rhs.accept(this);
            ExprResult rhsvalue=lastExpr;
            suf=curFunc.cnt++;
            curBlock.addIns(new Icmp("%"+suf,"ne","i1",rhsvalue.temp,"0"));

            curBlock=curFunc.addBlock(new IRBlock("logic.end."+ord));
            suf=curFunc.cnt++;
            Phi phi=new Phi("%"+suf,"i1");
            if(it.opCode.equals("&&")){
                phi.vals.add("0");
                phi.labels.add(ori);
                phi.vals.add("%"+(suf-1));
                phi.labels.add("logic.rhs."+ord);
            }else{
                phi.vals.add("1");
                phi.labels.add(ori);
                phi.vals.add("%"+(suf-1));
                phi.labels.add("logic.rhs."+ord);
            }
            curBlock.addIns(phi);
            lastExpr.temp="%"+suf;
            lastExpr.isConst=false;
            lastExpr.isPtr=false;
            return;
        }

        it.rhs.accept(this);
        ExprResult rhsvalue=lastExpr;
        int name=curFunc.cnt++;
        //TODO
        //字符串相关的处理还没进行
        if(it.opCode.equals("==")||it.opCode.equals("!=")||it.opCode.equals(">")||it.opCode.equals(">=")||it.opCode.equals("<")||it.opCode.equals("<=")){
            String type=new IRType(it.lhs.type).toString();
            Icmp obj=new Icmp("%"+name,it.opCode,type,lhsvalue.temp,rhsvalue.temp);
            curBlock.addIns(obj);
            lastExpr.isConst=false;
            lastExpr.temp="%"+name;
            lastExpr.isPtr=false;
        }else{
            String type=new IRType(it.lhs.type).toString();
            Binary obj=new Binary(it.opCode,type, lhsvalue.temp,rhsvalue.temp,"%"+name);
            curBlock.addIns(obj);
            lastExpr.isConst=false;
            lastExpr.temp="%"+name;
            lastExpr.isPtr=false;
        }
    }

    public void visit(ConditionExprNode it){
    }

    public void visit(FuncExprNode it){
    }

    public void visit(MemberExprNode it) {
    }

    public void visit(NewArrayExprNode it){
    }

    public void visit(NewVarExprNode it){
    }

    public void visit(UnaryExprNode it){
        it.expr.accept(this);
        int name=curFunc.cnt++;
        if(it.opCode.equals("!")){
            curBlock.addIns(new Binary("^","i1",lastExpr.temp,"1","%"+name));
        }else if(it.opCode.equals("~")){
            curBlock.addIns(new Binary("^","i32",lastExpr.temp,"-1","%"+name));
        }else if(it.opCode.equals("-")){
            curBlock.addIns(new Binary("-","i32","0",lastExpr.temp,"%"+name));
        }
        lastExpr.isPtr=false;
        lastExpr.temp="%"+name;
        lastExpr.isConst=false;
    }

    public void visit(PreExprNode it){//是左值，需要传个指针
        it.expr.accept(this);
        int name=curFunc.cnt++;
        if(it.opCode.equals("++")){
            curBlock.addIns(new Binary("+","i32",lastExpr.temp,"1","%"+name));
        }else{
            curBlock.addIns(new Binary("-","i32",lastExpr.temp,"1","%"+name));
        }
        curBlock.addIns(new Store("i32","%"+name,lastExpr.PtrName));
        lastExpr.isPtr=true;
        lastExpr.temp="%"+name;
        lastExpr.isConst=false;
        lastExpr.PtrName= lastExpr.PtrName;

    }

    public void visit(SufExprNode it){//不是左值
        it.expr.accept(this);
        int name=curFunc.cnt++;
        if(it.opCode.equals("++")){
            curBlock.addIns(new Binary("+","i32",lastExpr.temp,"1","%"+name));
        }else{
            curBlock.addIns(new Binary("-","i32",lastExpr.temp,"1","%"+name));
        }
        curBlock.addIns(new Store("i32","%"+name,lastExpr.PtrName));
        lastExpr.isPtr=false;
        lastExpr.isConst=false;
        lastExpr.temp=lastExpr.temp;
    }

    public void visit(ParenExprNode it){
        it.expr.accept(this);
    }

    public void visit(FStringExprNode it){
    }

    public void visit(ArrayConstNode it) {
    }
}
