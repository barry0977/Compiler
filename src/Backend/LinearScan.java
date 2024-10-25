package Backend;

import IR.IRBlock;
import IR.IRProgram;
import IR.instr.Instruction;
import IR.module.IRFuncDef;
import Optimize.DomTreeBuilder;
import Util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class LinearScan {
    public IRProgram program;
    public HashMap<Instruction,Integer>InstrOrder;//语句线性序
    public HashMap<String, Interval>LiveInterval;

    public static class Interval implements Comparable<Interval>{
        public String register;
        public int start,end;

        public Interval(int start, int end){
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
        InstrOrder = new HashMap<>();
        LiveInterval = new HashMap<>();
    }

    public void work(){
        for(var func:program.funcs){
            visitFunc(func);
        }
    }

    public void visitFunc(IRFuncDef func){
        SetLinearOrder(func);
        SetLiveInterval(func);
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
            for(var instr:block.statements){
                InstrOrder.put(instr,index++);
            }
            InstrOrder.put(block.terminalStmt,index++);
        }
    }

    //确定活跃区间
    //start应该是最早的out,end应该是最后的in
    public void SetLiveInterval(IRFuncDef func){
        for(var instr:func.entry.statements){
            int linear_order = InstrOrder.get(instr);
            for(var inVar:instr.in){
                if(LiveInterval.containsKey(inVar)){
                    LiveInterval.get(inVar).end=Math.max(linear_order,LiveInterval.get(inVar).end);
                }else{
                    LiveInterval.put(inVar,new Interval(linear_order,linear_order));
                }
            }
            for(var outVar:instr.out){
                if(LiveInterval.containsKey(outVar)){
                    LiveInterval.get(outVar).start=Math.min(linear_order,LiveInterval.get(outVar).start);
                }else{
                    LiveInterval.put(outVar,new Interval(linear_order,linear_order));
                }
            }
        }
        int entry_terminal_order=InstrOrder.get(func.entry.terminalStmt);
        for(var invar:func.entry.terminalStmt.in){
            if(LiveInterval.containsKey(invar)){
                LiveInterval.get(invar).end=Math.max(entry_terminal_order,LiveInterval.get(invar).end);
            }else{
                LiveInterval.put(invar, new Interval(entry_terminal_order,entry_terminal_order));
            }
        }
        for(var outvar:func.entry.terminalStmt.out){
            if(LiveInterval.containsKey(outvar)){
                LiveInterval.get(outvar).start=Math.min(entry_terminal_order,LiveInterval.get(outvar).start);
            }else{
                LiveInterval.put(outvar, new Interval(entry_terminal_order,entry_terminal_order));
            }
        }

        for(var block:func.body){
            for(var phiinstr:block.philist.values()){
                //TODO
            }
            for(var instr:block.statements){
                int linear_order=InstrOrder.get(instr);
                for(var inVar:instr.in){
                    if(LiveInterval.containsKey(inVar)){
                        LiveInterval.get(inVar).end=Math.max(linear_order,LiveInterval.get(inVar).end);
                    }else{
                        LiveInterval.put(inVar,new Interval(linear_order,linear_order));
                    }
                }
                for(var outVar:instr.out){
                    if(LiveInterval.containsKey(outVar)){
                        LiveInterval.get(outVar).start=Math.min(linear_order,LiveInterval.get(outVar).start);
                    }else{
                        LiveInterval.put(outVar,new Interval(linear_order,linear_order));
                    }
                }
            }
            int terminal_order=InstrOrder.get(block.terminalStmt);
            for(var invar:block.terminalStmt.in){
                if(LiveInterval.containsKey(invar)){
                    LiveInterval.get(invar).end=Math.max(terminal_order,LiveInterval.get(invar).end);
                }else{
                    LiveInterval.put(invar,new Interval(terminal_order,terminal_order));
                }
            }
            for(var outvar:block.terminalStmt.out){
                if(LiveInterval.containsKey(outvar)){
                    LiveInterval.get(outvar).start=Math.min(terminal_order,LiveInterval.get(outvar).start);
                }else{
                    LiveInterval.put(outvar,new Interval(terminal_order,terminal_order));
                }
            }
        }
    }


    //寄存器分配
    public void AllocateRegister(){
        PriorityQueue<Interval>IntervalOrder=new PriorityQueue<>();
        IntervalOrder.addAll(LiveInterval.values());
        while(!IntervalOrder.isEmpty()){
            Interval cur= IntervalOrder.poll();

        }
    }
}
