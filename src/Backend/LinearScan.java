package Backend;

import IR.IRBlock;
import IR.IRProgram;
import IR.instr.Instruction;
import IR.module.IRFuncDef;
import Optimize.DomTreeBuilder;

import java.util.*;

public class LinearScan {
    public IRProgram program;
    public HashMap<Instruction,Integer>InstrOrder;//语句线性序
    public HashMap<String, Interval>LiveInterval;

    public static class Interval implements Comparable<Interval>{
        public String register;
        public int start,end;

        public Interval(String register,int start, int end){
            this.register = register;
            this.start = start;
            this.end = end;
        }

        //按照左端点大小进行排序
        @Override
        public int compareTo(Interval other) {
            if(this.start!=other.start){
                return Integer.compare(this.start,other.start);
            }
            else{
                return Integer.compare(this.end,other.end);
            }
        }
    }


    public LinearScan(IRProgram program) {
        this.program=program;
        InstrOrder = new HashMap<>();
        LiveInterval = new HashMap<>();
    }

    public void work(){
        for(var func:program.funcs){
            init();
            visitFunc(func);
        }
    }

    void init(){
        InstrOrder.clear();
        LiveInterval.clear();
    }

    public void visitFunc(IRFuncDef func){
        SetLinearOrder(func);
        SetLiveInterval(func);
        AllocateRegister(func);
    }

    //确定语句线性序
    public void SetLinearOrder(IRFuncDef func){
        //获得CFG逆后序
        ArrayList<IRBlock>RPO = new DomTreeBuilder(program).ReversePostOrder(func);
        int index=0;
        for(var block:RPO){
            for(var phiinstr:block.philist.values()){
                InstrOrder.put(phiinstr,index);//每个块中phi并行执行，线性序应相同
            }
            if(!block.philist.isEmpty()){
                index++;
            }
            for(var instr:block.statements){
                InstrOrder.put(instr,index++);
            }
            InstrOrder.put(block.terminalStmt,index++);
        }
    }

    //确定活跃区间
    //start应该是最早的out,end应该是最后的in
    public void SetLiveInterval(IRFuncDef func){
        for(int i=0;i<Math.min(8,func.paramnames.size());i++){
            LiveInterval.put(func.paramnames.get(i),new Interval(func.paramnames.get(i),0,0));
        }
        for(var instr:func.entry.statements){
            int linear_order = InstrOrder.get(instr);
            for(var inVar:instr.in){
                if(LiveInterval.containsKey(inVar)){
                    LiveInterval.get(inVar).end=Math.max(linear_order,LiveInterval.get(inVar).end);
                }else{
                    LiveInterval.put(inVar,new Interval(inVar,linear_order,linear_order));
                }
            }
            for(var outVar:instr.out){
                if(LiveInterval.containsKey(outVar)){
                    LiveInterval.get(outVar).start=Math.min(linear_order,LiveInterval.get(outVar).start);
                }else{
                    LiveInterval.put(outVar,new Interval(outVar,linear_order,linear_order));
                }
            }
        }
        int entry_terminal_order=InstrOrder.get(func.entry.terminalStmt);
        for(var invar:func.entry.terminalStmt.in){
            if(LiveInterval.containsKey(invar)){
                LiveInterval.get(invar).end=Math.max(entry_terminal_order,LiveInterval.get(invar).end);
            }else{
                LiveInterval.put(invar, new Interval(invar,entry_terminal_order,entry_terminal_order));
            }
        }
        for(var outvar:func.entry.terminalStmt.out){
            if(LiveInterval.containsKey(outvar)){
                LiveInterval.get(outvar).start=Math.min(entry_terminal_order,LiveInterval.get(outvar).start);
            }else{
                LiveInterval.put(outvar, new Interval(outvar,entry_terminal_order,entry_terminal_order));
            }
        }

        for(var block:func.body){
            for(var phiinstr:block.philist.values()){
                int linear_order=InstrOrder.get(phiinstr);
                var result=phiinstr.result;
                if(LiveInterval.containsKey(result)){
                    LiveInterval.get(result).start=Math.min(LiveInterval.get(result).start,linear_order);
                }else{
                    LiveInterval.put(result,new Interval(result,linear_order,linear_order));
                }
            }
            for(var instr:block.statements){
                int linear_order=InstrOrder.get(instr);
                for(var inVar:instr.in){
                    if(LiveInterval.containsKey(inVar)){
                        LiveInterval.get(inVar).end=Math.max(linear_order,LiveInterval.get(inVar).end);
                    }else{
                        LiveInterval.put(inVar,new Interval(inVar,linear_order,linear_order));
                    }
                }
                for(var outVar:instr.out){
                    if(LiveInterval.containsKey(outVar)){
                        LiveInterval.get(outVar).start=Math.min(linear_order,LiveInterval.get(outVar).start);
                    }else{
                        LiveInterval.put(outVar,new Interval(outVar,linear_order,linear_order));
                    }
                }
            }
            int terminal_order=InstrOrder.get(block.terminalStmt);
            for(var invar:block.terminalStmt.in){
                if(LiveInterval.containsKey(invar)){
                    LiveInterval.get(invar).end=Math.max(terminal_order,LiveInterval.get(invar).end);
                }else{
                    LiveInterval.put(invar,new Interval(invar,terminal_order,terminal_order));
                }
            }
            for(var outvar:block.terminalStmt.out){
                if(LiveInterval.containsKey(outvar)){
                    LiveInterval.get(outvar).start=Math.min(terminal_order,LiveInterval.get(outvar).start);
                }else{
                    LiveInterval.put(outvar,new Interval(outvar,terminal_order,terminal_order));
                }
            }
        }
    }


    //寄存器分配
    //可用的寄存器:a0-a7(参数已经不活跃),t0-t3,s0-s11
    //尽量使用a0-a7和t0-t3
    //对应序号0-7,8-11,12-23
    public void AllocateRegister(IRFuncDef func){
        int RegS_size=0;//使用的s开头的寄存器
        PriorityQueue<Interval>IntervalOrder=new PriorityQueue<>();
        int[] usedPeriod=new int[24];//记录目前占用寄存器的变量的活跃end
        BitSet freeReg=new BitSet(24);//0表示空闲，1表示被占用
        for(int i=0;i<Math.min(8,func.paramnames.size());i++){//a0-a7一开始被参数占用
            freeReg.set(i);
            usedPeriod[i]=LiveInterval.get(func.paramnames.get(i)).end;
        }
        IntervalOrder.addAll(LiveInterval.values());
        while(!IntervalOrder.isEmpty()){
            Interval cur= IntervalOrder.poll();

            //先清理此时刻已经不活跃的寄存器
            int busy_bit = freeReg.nextSetBit(0);
            while(busy_bit!=-1){
                if(usedPeriod[busy_bit]<cur.start){
                    freeReg.clear(busy_bit);
                }
                busy_bit=freeReg.nextSetBit(busy_bit+1);
            }

            boolean isAllocated=false;
            for(int i=0;i<24;i++){
                if(!freeReg.get(i)){//找到了空闲的寄存器
                    freeReg.set(i);
                    usedPeriod[i]=cur.end;
                    RegS_size=Math.max(RegS_size,i-11);//计算使用的s寄存器的最大值
                    isAllocated=true;
                    func.RegAlloc.put(cur.register,i);
                    break;
                }
            }
            if(!isAllocated){//没有分配寄存器，要溢出到栈上
                func.SpilledVar.add(cur.register);
                func.stacksize+=4;
            }
        }
        func.Regs_size=RegS_size;
    }
}
