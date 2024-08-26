package Frontend;

import AST.ASTVisitor;
import AST.Def.*;
import AST.Expr.*;
import AST.Expr.BasicExpr.ArrayConstNode;
import AST.Expr.BasicExpr.BasicExprNode;
import AST.ProgramNode;
import AST.Stmt.*;
import AST.Type.Type;
import AST.Type.exprType;
import IR.IRBlock;
import IR.instr.*;
import IR.module.*;
import IR.IRProgram;
import IR.type.ExprResult;
import IR.type.IRType;
import Util.Decl.ClassDecl;
import Util.Decl.FuncDecl;
import Util.error.semanticError;
import Util.scope.*;

import java.util.ArrayList;

public class IRBuilder implements ASTVisitor {
    IRProgram program;
    globalScope gScope;
    Scope curScope;
    IRBlock curBlock;
    ExprResult lastExpr;//最后一次运算出的结果
    IRFuncDef curFunc;
    IRFuncDef Init;

    public IRBuilder(IRProgram program, globalScope gscope) {
        this.program = program;
        this.gScope = gscope;
        this.curScope = gscope;
    }

    public void visit(ProgramNode it){
        Init=new IRFuncDef("_init","void");
        program.funcs.add(Init);
        IRStringDef truedef=new IRStringDef("true");
        truedef.label="true";
        IRStringDef falsedef=new IRStringDef("false");
        truedef.label="false";
        for(var def:it.defNodes){
            def.accept(this);
        }
    }

    public void visit(ClassDefNode it){
        curScope=it.scope2;
        IRClassDef classDef = new IRClassDef();
        classDef.name=it.name;
        for(var mem:it.vars){
            classDef.members.add(new IRType(mem.vartype));
        }
        program.classs.add(classDef);
        for(var func:it.funcs){
            func.accept(this);
        }
        it.construct.accept(this);
        curScope=curScope.parent;
    }

    public void visit(FuncDefNode it){
        curScope=it.scope2;
        IRFuncDef funcDef=new IRFuncDef();
        if(curScope instanceof globalScope){//全局函数
            funcDef.name=it.name;
        }else{//类方法，名字修改，参数要加上this
            funcDef.name=((classScope) curScope.parent).classname+"::"+it.name;//名字前加上类名
            funcDef.paramnames.add("%this");
            funcDef.paramtypes.add(new IRType("ptr"));
            Alloca ins1=new Alloca("%this.addr","ptr");
            funcDef.entry.addIns(ins1);
            Store ins2=new Store("ptr","%this","%this."+curScope.depth+"."+curScope.order);
            funcDef.entry.addIns(ins2);
            funcDef.entry.addIns(new Load("%this.this","ptr","this.addr"));//用于在成员函数中代替this
        }
        for(var args:it.paraslist.Paralist){//加入参数列表
            funcDef.paramnames.add("%"+args.second);
            funcDef.paramtypes.add(new IRType(args.first));
            Alloca ins1=new Alloca();
            ins1.result="%"+args.second+"."+curScope.depth+"."+curScope.order;//参数指针名字加上addr
            ins1.type=new IRType(args.first).toString();//这里alloca的类应该都是ptr
            funcDef.entry.addIns(ins1);
            Store ins2=new Store();
            ins2.type=new IRType(args.first).toString();
            ins2.value="%"+args.second;
            ins2.pointer="%"+args.second+"."+curScope.depth+"."+curScope.order;
            funcDef.entry.addIns(ins2);
        }
        funcDef.returntype=new IRType(it.returntype);
        program.funcs.add(funcDef);
        this.curFunc=funcDef;
        this.curBlock=funcDef.entry;
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
                    program.globalvars.add(gvar);
                }else{
                    if(variable.second instanceof BasicExprNode && (((BasicExprNode) variable.second).isInt||((BasicExprNode) variable.second).isFalse||((BasicExprNode) variable.second).isTrue||((BasicExprNode) variable.second).isNull)){
                        IRGlobalVarDef gvar=new IRGlobalVarDef(variable.first,new IRType(it.vartype),((BasicExprNode) variable.second).value);
                        program.globalvars.add(gvar);
                    }else{//用变量初始化
                        IRGlobalVarDef gvar=new IRGlobalVarDef(variable.first,new IRType(it.vartype),"null");
                        program.globalvars.add(gvar);
                        //在init函数中初始化
                        curFunc=Init;
                        curBlock=curFunc.entry;
                        variable.second.accept(this);
                        curBlock.addIns(new Store(new IRType(it.vartype).toString(), lastExpr.temp,"@"+variable.first));
                    }
                }
            }
        }else{//局部变量
            for(var variable:it.vars){
                Alloca ins=new Alloca();
                ins.result="%"+variable.first+"."+curScope.depth+"."+curScope.order;
                ins.type=new IRType(it.vartype).toString();
                curFunc.entry.addIns(ins);
                if(variable.second!=null){
                    variable.second.accept(this);
                    curFunc.entry.addIns(new Store(ins.type,lastExpr.temp,ins.result));
                }
            }
        }
        for(var x:it.vars){
            if(!(curScope instanceof classScope)){//类里面的变量symbolcollect时已经加过了
                curScope.addVar(x.first,it.vartype,it.pos);
            }
        }
    }

    public void visit(ConstructNode it){
        curScope=new funcScope(curScope,new Type("void",0));
        IRFuncDef funcDef=new IRFuncDef();
        funcDef.name=it.name+"::"+it.name;
        funcDef.paramnames.add("%this");
        funcDef.paramtypes.add(new IRType("ptr"));
        funcDef.entry.addIns(new Alloca("%this.addr","ptr"));
        funcDef.entry.addIns(new Store("ptr","%this","%this.addr"));
        funcDef.returntype=new IRType("void");
        curBlock=funcDef.entry;
        program.funcs.add(funcDef);
        this.curFunc=funcDef;
        this.curBlock=funcDef.entry;
        for(var stmt:it.stmts){
            stmt.accept(this);
        }
        curScope=curScope.parent;
    }

    public void visit(ParalistNode it){
    }

    public void visit(IfStmtNode it){
        it.condition.accept(this);
        curBlock.addIns(new Br(lastExpr.temp,"if.then."+curScope.depth+"."+curScope.order,"if.else."+curScope.depth+"."+curScope.order));

        curBlock=curFunc.addBlock(new IRBlock("if.then."+curScope.depth+"."+curScope.order));
        if(it.trueStmt != null){
            curScope=new Scope(curScope);
            if(it.trueStmt instanceof BlockStmtNode){
                for(var stmt:((BlockStmtNode) it.trueStmt).statements){
                    stmt.accept(this);
                }
            }else{
                it.trueStmt.accept(this);
            }
            curScope=curScope.parent;
        }
        curBlock.addIns(new Br("if.end."+curScope.depth+"."+curScope.order));

        curBlock=curFunc.addBlock(new IRBlock("if.else."+curScope.depth+"."+curScope.order));
        if(it.falseStmt != null){
            curScope=new Scope(curScope);
            if(it.falseStmt instanceof BlockStmtNode){
                for(var stmt:((BlockStmtNode) it.falseStmt).statements){
                    stmt.accept(this);
                }
            }else{
                it.falseStmt.accept(this);
            }
            curScope=curScope.parent;
        }
        curBlock.addIns(new Br("if.end."+curScope.depth+"."+curScope.order));

        curBlock=curFunc.addBlock(new IRBlock("if.end."+curScope.depth+"."+curScope.order));
    }

    public void visit(WhileStmtNode it){
        curScope=new loopScope(curScope);
        curBlock.addIns(new Br("while.cond."+curScope.depth+"."+curScope.order));

        curBlock=curFunc.addBlock(new IRBlock("while.cond."+curScope.depth+"."+curScope.order));
        it.condition.accept(this);
        curBlock.addIns(new Br(lastExpr.temp,"while.body."+curScope.depth+"."+curScope.order,"while.end"+curScope.depth+"."+curScope.order));

        curBlock=curFunc.addBlock(new IRBlock("while.body."+curScope.depth+"."+curScope.order));
        if(it.body!=null){
            if(it.body instanceof BlockStmtNode){
                for(var stmt:((BlockStmtNode) it.body).statements){
                    stmt.accept(this);
                }
            }else{
                it.body.accept(this);
            }
        }
        curBlock.addIns(new Br("while.cond."+curScope.depth+"."+curScope.order));

        curBlock=curFunc.addBlock(new IRBlock("while.end."+curScope.depth+"."+curScope.order));
        curScope=curScope.parent;
    }

    public void visit(ForStmtNode it){
        it.scope=new loopScope(curScope);
        it.scope.isFor=true;
        curScope=it.scope;
        if(it.initStmt != null){
            it.initStmt.accept(this);
        }
        curBlock.addIns(new Br("for.cond."+curScope.depth+"."+curScope.order));

        curBlock=curFunc.addBlock(new IRBlock("for.cond."+curScope.depth+"."+curScope.order));
        if(it.condition != null){
            it.condition.accept(this);
            curBlock.addIns(new Br(lastExpr.temp,"for.body."+curScope.depth+"."+curScope.order,"for.end."+curScope.depth+"."+curScope.order));
        }else{
            curBlock.addIns(new Br("for.body."+curScope.depth+"."+curScope.order));
        }

        curBlock=curFunc.addBlock(new IRBlock("for.body."+curScope.depth+"."+curScope.order));
        if(it.body!=null){
            if(it.body instanceof BlockStmtNode){
                for(var stmt:((BlockStmtNode) it.body).statements){
                    stmt.accept(this);
                }
            }else{
                it.body.accept(this);
            }
        }
        curBlock.addIns(new Br("for.step."+curScope.depth+"."+curScope.order));

        curBlock=curFunc.addBlock(new IRBlock("for.step."+curScope.depth+"."+curScope.order));
        if(it.nextstep != null){
            it.nextstep.accept(this);
        }
        curBlock.addIns(new Br("for.cond."+curScope.depth+"."+curScope.order));

        curBlock=curFunc.addBlock(new IRBlock("for.end."+curScope.depth+"."+curScope.order));
        curScope=curScope.parent;
    }

    public void visit(BreakStmtNode it){
        loopScope loop=curScope.getLoopScope();
        if(loop.isWhile){
            curBlock.addIns(new Br("while.end."+loop.depth+"."+loop.order));
        }else{
            curBlock.addIns(new Br("for.end."+loop.depth+"."+loop.order));
        }
    }

    public void visit(ContinueStmtNode it){
        loopScope loop=curScope.getLoopScope();
        if(loop.isWhile){
            curBlock.addIns(new Br("while.cond."+loop.depth+"."+loop.order));
        }else{
            curBlock.addIns(new Br("for.step."+loop.depth+"."+loop.order));
        }
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
        curScope=new Scope(curScope);
        for(var stmt:it.statements){
            stmt.accept(this);
        }
        curScope=curScope.parent;
    }

    public void visit(VardefStmtNode it){
        it.varDef.accept(this);
    }

    public void visit(EmptyStmtNode it){}

    public void visit(ArrayExprNode it){
        //TODO
    }

    public void visit(AssignExprNode it){
        it.lhs.accept(this);
        var obj=lastExpr.PtrName;
        it.rhs.accept(this);
        var value=lastExpr.temp;

        if(it.lhs.type.dim==0&&it.lhs.type.isString){
            curBlock.addIns(new Store("ptr",value,obj));
        }else{
            String type=new IRType(it.lhs.type).toString();
            curBlock.addIns(new Store(type,value,obj));
        }
        lastExpr.temp=lastExpr.temp;//返回右值？
        lastExpr.isPtr=false;
    }

    public void visit(BasicExprNode it){
        if(it.isIdentifier){
            if(it.type.isFunc){//函数(可能会在类方法里面调用另一个类方法，这里会被识别到)
                classScope obj=curScope.getClassScope();
                if(obj!= null){//如果是类方法，需要传递this指针
                    lastExpr.isPtr=true;
                    lastExpr.PtrName="%this.this";
                    lastExpr.PtrType="%class."+obj.classname;
                }
            }else{//变量(变量都是左值，需要记下指针)
                var target=curScope.findVarScope(it.name);
                if(target instanceof classScope){//类的成员变量
                    Getelementptr ins=new Getelementptr();
                    int index=curFunc.cnt++;
                    ins.result="%"+index;//用匿名变量来记载成员变量的指针
                    ins.pointer="%this.this";//代替this指针
                    ins.types.add("i32");
                    ins.idx.add("0");
                    ins.types.add("i32");
                    int varid=((classScope) target).getVarIndex(it.name);//获取当前成员变量在类中的位置
                    ins.idx.add(varid+"");
                    ins.type="%class."+((classScope) target).classname;//this指针指向的类型是类
                    curBlock.addIns(ins);
                    lastExpr.PtrName=ins.result;
                }else if(target instanceof globalScope){//全局变量
                    lastExpr.PtrName="@"+it.name;
                }else{//局部变量
                    lastExpr.PtrName="%"+it.name+"."+target.depth+"."+target.order;
                }
                int numname=curFunc.cnt++;
                lastExpr.temp="%"+numname;
                lastExpr.isPtr=true;
                lastExpr.PtrType=it.type.getType();//指针指向的类型
                lastExpr.isConst=false;
                curBlock.addIns(new Load(lastExpr.temp,new IRType(it.type).toString(),lastExpr.PtrName));
            }
        }else if(it.isThis){
            lastExpr.PtrName="%this.this";
            lastExpr.isPtr=true;
            lastExpr.PtrType=it.type.getType();
        }else if(it.isString){//字符串常量需要定义为全局变量
            IRStringDef def=new IRStringDef(gScope.strcnt++,it.value,false);
            program.globalvars.add(def);
            lastExpr.isPtr=true;
            lastExpr.PtrName="@.str."+def.label;
            lastExpr.PtrType="ptr";
        }else if(it.isNull){
            //TODO
        }else{

        }

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
        if(it.lhs.type.dim==0&&it.lhs.type.isString){//如果是字符串
            Call ins=new Call("%"+name,"ptr","string.add");
            if(it.opCode.equals("+")){
                ins.FunctionName="string.add";
            }else if(it.opCode.equals("==")){
                ins.FunctionName="string.equal";
            }else if(it.opCode.equals("!=")){
                ins.FunctionName="string.notEqual";
            }else if(it.opCode.equals("<")){
                ins.FunctionName="string.less";
            }else if(it.opCode.equals("<=")){
                ins.FunctionName="string.lessOrEqual";
            }else if(it.opCode.equals(">")){
                ins.FunctionName="string.greater";
            }else{
                ins.FunctionName="string.greaterOrEqual";
            }
            ins.ArgsTy.add("ptr");
            ins.ArgsVal.add(lhsvalue.PtrName);
            ins.ArgsTy.add("ptr");
            ins.ArgsVal.add(rhsvalue.PtrName);
            curBlock.addIns(ins);
            lastExpr.temp="%"+name;
            lastExpr.isConst=false;
            lastExpr.isPtr=false;
            return;
        }
        if(it.opCode.equals("==")||it.opCode.equals("!=")||it.opCode.equals(">")||it.opCode.equals(">=")||it.opCode.equals("<")||it.opCode.equals("<=")){
            String type=new IRType(it.lhs.type).toString();
            Icmp obj=new Icmp("%"+name,it.opCode,type,lhsvalue.temp,rhsvalue.temp);
            curBlock.addIns(obj);
        }else{
            String type=new IRType(it.lhs.type).toString();
            Binary obj=new Binary(it.opCode,type, lhsvalue.temp,rhsvalue.temp,"%"+name);
            curBlock.addIns(obj);
        }
        lastExpr.isConst=false;
        lastExpr.temp="%"+name;
        lastExpr.isPtr=false;
    }

    public void visit(ConditionExprNode it){
        it.cond_.accept(this);
        var condvalue=lastExpr.temp;
        it.then_.accept(this);
        var truevalue=lastExpr.temp;
        it.else_.accept(this);
        var falsevalue=lastExpr.temp;
        int name=curFunc.cnt++;
        String type=new IRType(it.then_.type).toString();
        curBlock.addIns(new Select("%"+name,condvalue,type,truevalue,falsevalue));
        lastExpr.temp="%"+name;
        lastExpr.isConst=false;
        lastExpr.isPtr=false;
    }


    public void visit(FuncExprNode it){
        it.func.accept(this);
        var func=it.func.type.funcinfo;
        String funcname;
        if(it.isClass){//调用类的方法
            funcname=lastExpr.PtrType+"::"+func.name;
            if(it.func instanceof MemberExprNode){
                if(((MemberExprNode) it.func).classname.equals("string")){//如果是字符串的成员函数，要特殊处理名字
                    funcname="string."+func.name;
                }
            }
        }else{//调用普通函数
            funcname=func.name;
        }
        String retype=new IRType(func.returnType).toString();
        int name=curFunc.cnt++;
        Call ins=new Call("%"+name,retype,funcname);
        if(it.isClass){//如果是类方法，第一个参数要加入前面获得的代表this的指针
            ins.ArgsTy.add("ptr");
            ins.ArgsVal.add(lastExpr.PtrName);//前面会访问memberExpr,获取类的this指针
        }
        for(var arg:it.args){
            arg.accept(this);
            var argsvalue=lastExpr.temp;
            String argstype=new IRType(arg.type).toString();
            ins.ArgsTy.add(argstype);
            ins.ArgsVal.add(argsvalue);
        }
        curBlock.addIns(ins);
        lastExpr.temp="%"+name;
        lastExpr.isConst=false;
        lastExpr.isPtr=false;
    }

    public void visit(MemberExprNode it) {
        it.obj.accept(this);//获取属于的类的this指针
        if(it.obj.type.dim>0){//数组，只能调用size
            //TODO
        }else{
            //寻找所在的类
            ClassDecl class_ = gScope.getClass(it.obj.type.typeName);//从全局变量里面找到类
            //是否是函数
            FuncDecl func_ = class_.funcs.get(it.member);
            if (func_ != null) {//如果是函数，那么需要保存属于的类对象的指针(由于前面访问返回的指针就是this，所以不用修改)
                lastExpr.isPtr=true;
                lastExpr.PtrName=lastExpr.PtrName;
                lastExpr.PtrType=lastExpr.PtrType;
                return;
            }
            //是否是变量
            Type var = class_.vars.get(it.member);
            if (var != null) {
                int ptrname=curFunc.cnt++;
                Getelementptr ins=new Getelementptr();
                ins.result="%"+ptrname;//获取的指针名
                ins.pointer=lastExpr.PtrName;
                ins.type=lastExpr.PtrType;
                int varid=class_.getIndex(it.member);//是类中的第几个变量
                ins.types.add("i32");
                ins.idx.add("0");
                ins.types.add("i32");
                ins.idx.add(varid+"");
                curBlock.addIns(ins);//获取该成员变量
                lastExpr.isPtr=true;
                lastExpr.PtrName= ins.result;
                lastExpr.PtrType=var.getType();
                int tmp=curFunc.cnt++;
                lastExpr.temp="%"+tmp;
                lastExpr.isConst=false;
                curBlock.addIns(new Load(lastExpr.temp,new IRType(var).toString(),lastExpr.PtrName));//Load指令获取临时值
                return;
            }
        }
    }

    public void visit(NewArrayExprNode it){
    }

    public void visit(NewVarExprNode it){
        Call ins=new Call()
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
        ArrayList<String>exprlist=new ArrayList<>();
        ArrayList<String>strlist=new ArrayList<>();
        for(var str:it.stringlist){
            IRStringDef strdef=new IRStringDef(gScope.strcnt++,str,true);
            program.globalvars.add(strdef);
            strlist.add("@.str."+strdef.label);
        }
        for(var expr:it.exprlist){
            expr.accept(this);
            if(expr.type.isString){
            }else if(expr.type.isInt){
                int tmp=curFunc.cnt++;
                Call ins=new Call("%"+tmp,"ptr","toString");
                ins.ArgsTy.add("i32");
                ins.ArgsVal.add(lastExpr.temp);
                curBlock.addIns(ins);
                lastExpr.temp="%"+tmp;
                lastExpr.isConst=false;
                lastExpr.isPtr=false;
            }else{
                int tmp=curFunc.cnt++;
                Select ins=new Select("%"+tmp,lastExpr.temp,"ptr","@.str.true","@.str.false");
                curBlock.addIns(ins);
                lastExpr.temp="%"+tmp;
                lastExpr.isConst=false;
                lastExpr.isPtr=false;
            }
            exprlist.add(lastExpr.temp);
        }
        lastExpr.temp=strlist.get(0);
        lastExpr.isConst=false;
        lastExpr.isPtr=false;
        for(int i=0;i<exprlist.size();i++){
            int tmp=curFunc.cnt++;
            Call ins=new Call("%"+tmp,"ptr","string.add");
            ins.ArgsTy.add("ptr");
            ins.ArgsVal.add(exprlist.get(i));
            ins.ArgsTy.add("ptr");
            ins.ArgsVal.add(strlist.get(i+1));
            curBlock.addIns(ins);
            int ans=curFunc.cnt++;
            Call ins2=new Call("%"+ans,"ptr","string.add");
            ins2.ArgsTy.add("ptr");
            ins2.ArgsVal.add(lastExpr.temp);
            ins2.ArgsTy.add("ptr");
            ins2.ArgsVal.add("%"+tmp);
            curBlock.addIns(ins2);
            lastExpr.temp="%"+ans;
        }
    }

    public void visit(ArrayConstNode it) {

    }
}
