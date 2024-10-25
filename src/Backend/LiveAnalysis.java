package Backend;

import IR.IRBlock;
import IR.IRProgram;
import IR.instr.Instruction;
import IR.instr.Ret;
import IR.module.IRFuncDef;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

public class LiveAnalysis {
    public IRProgram program;

    public LiveAnalysis(IRProgram program) {
        this.program = program;
    }

    public void work(){
        for(var func:program.funcs){
            runLiveAnalysis(func);
        }
    }

    //确定每条指令的前驱和后继,获取每条指令的def和use
    public void GetPrepareWork(IRFuncDef func,ArrayList<Instruction>exit){
        for(int i=0;i<func.entry.statements.size();i++){
            Instruction instr = func.entry.statements.get(i);
            instr.getUseDef();
            if(i<func.entry.statements.size()-1){
                instr.succs.add(func.entry.statements.get(i+1));
            }else{
                func.entry.terminalStmt.preds.add(instr);
                instr.succs.add(func.entry.terminalStmt);
            }
            if(i>0){
                instr.preds.add(func.entry.statements.get(i-1));
            }
        }
        func.entry.terminalStmt.getUseDef();
        if(func.entry.terminalStmt instanceof Ret){
            exit.add(func.entry.terminalStmt);
        }
        for(var successor:func.entry.succ){
            if(successor.statements.isEmpty()){
                successor.terminalStmt.preds.add(func.entry.terminalStmt);
                func.entry.terminalStmt.succs.add(successor.terminalStmt);
            }else{
                successor.statements.get(0).preds.add(func.entry.terminalStmt);
                func.entry.terminalStmt.succs.add(successor.statements.get(0));
            }
        }

        for(int i=0;i<func.body.size();i++){
            IRBlock block = func.body.get(i);
            for(int j=0;j<block.statements.size();j++){
                Instruction instr = block.statements.get(j);
                instr.getUseDef();
                if(j<block.statements.size()-1){
                    instr.succs.add(block.statements.get(j+1));
                }else{
                    block.terminalStmt.preds.add(instr);
                    instr.succs.add(block.terminalStmt);
                }
                if(j>0){
                    instr.preds.add(block.statements.get(j-1));
                }
            }
            block.terminalStmt.getUseDef();
            if(block.terminalStmt instanceof Ret){
                exit.add(block.terminalStmt);
            }
            for(var successor:block.succ){
                if(successor.statements.isEmpty()){
                    successor.terminalStmt.preds.add(block.terminalStmt);
                    block.terminalStmt.succs.add(successor.terminalStmt);
                }else{
                    successor.statements.get(0).preds.add(block.terminalStmt);
                    block.terminalStmt.succs.add(successor.statements.get(0));
                }
            }
        }
    }

    public void runLiveAnalysis(IRFuncDef func){
        ArrayList<Instruction>exit=new ArrayList<>();
        GetPrepareWork(func,exit);
        //通过不动点迭代，从出口开始倒序BFS遍历控制流图，直到一次完整迭代前后没有变动
        boolean changed = true;
        while(changed){
            changed = false;
            ArrayDeque<Instruction> queue = new ArrayDeque<>(exit);
            //可能会出现环，要记录是否访问，防止无法结束
            HashSet<Instruction> visited = new HashSet<>(exit);
            while(!queue.isEmpty()){
                Instruction instr = queue.poll();
                HashSet<String>new_in=new HashSet<>();
                HashSet<String>new_out=new HashSet<>();
                for(var pred:instr.preds){
                    if(!visited.contains(pred)){
                        visited.add(pred);
                        queue.add(pred);
                    }
                }
                for(var succ:instr.succs){//先计算out，对于出口指令，out[exit]=0
                    new_out.addAll(succ.in);
                }
                new_in.addAll(instr.use);
                new_in.addAll(new_out);
                new_in.removeAll(instr.def);
                if(!(new_in.equals(instr.in)&&new_out.equals(instr.out))){
                    changed = true;
                }
                instr.in=new_in;
                instr.out=new_out;
            }
        }
    }
}
