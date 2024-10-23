package Backend;

import IR.IRBlock;
import IR.IRProgram;
import IR.instr.Instruction;
import IR.module.IRFuncDef;
import Optimize.DomTreeBuilder;
import Util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class LinearScan {
    public IRProgram program;
    public HashMap<Instruction,Integer>InstrOrder;//语句线性序
    public HashMap<String, Pair<Integer,Integer>>LiveInterval;

    public LinearScan(IRProgram program) {
        InstrOrder = new HashMap<>();
        LiveInterval = new HashMap<>();
    }
    //确定语句线性序
    public void SetLinearOrder(IRFuncDef func){
        //获得CFG逆后序
        ArrayList<IRBlock>RPO = new DomTreeBuilder(program).ReversePostOrder(func);
        int index=0;
        for(var block:RPO){
            for(var instr:block.statements){
                InstrOrder.put(instr,index++);
            }
            InstrOrder.put(block.terminalStmt,index++);
        }
    }

    //确定活跃区间
    public void SetLiveInterval(){

    }

    //活跃区间排序
    public void ReorderLiveInterval(){

    }

    //寄存器分配
    public void AllocateRegister(){

    }
}
