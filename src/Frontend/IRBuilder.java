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
    }

    public void visit(ReturnStmtNode it){
    }

    public void visit(BlockStmtNode it){
    }

    public void visit(VardefStmtNode it){
    }

    public void visit(EmptyStmtNode it){

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
            Icmp
            Br ins1=new Br();
            ins1.haveCondition=true;
            ins1.cond=lhsvalue.temp;
            ins1.iftrue=
            curBlock=curFunc.addBlock(new IRBlock("logic.rhs."+ord));//还需要执行右操作数

            IRBlock toend=new IRBlock("logic.end."+ord);
            it.rhs.accept(this);
            ExprResult rhsvalue=lastExpr;
            return;
        }
        it.rhs.accept(this);
        ExprResult rhsvalue=lastExpr;


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
    }

    public void visit(PreExprNode it){
    }

    public void visit(SufExprNode it){
    }

    public void visit(ParenExprNode it){
    }

    public void visit(FStringExprNode it){
    }

    public void visit(ArrayConstNode it) {
    }
}
