package Backend;

import IR.IRBlock;
import IR.IRProgram;
import IR.instr.Br;
import IR.instr.Instruction;
import IR.instr.Ret;
import IR.instr.Select;
import IR.module.IRFuncDef;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void GetPrepareWork(IRFuncDef func,ArrayList<Instruction>exit,HashMap<String,HashSet<String>>phidefs){
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
            phidefs.put(block.label,new HashSet<String>());
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
            //phi中的use实际是在对应前驱块的最后被use
            for(var phiIns:block.philist.values()){
                phidefs.get(block.label).add(phiIns.result);
                for(int k=0;k<phiIns.vals.size();k++){
                    String val=phiIns.vals.get(k);
                    if(val!=null && val.charAt(0)=='%'){
                        for(var predBlock:block.pred){
                            if(predBlock.label.equals(phiIns.labels.get(k))){
                                predBlock.terminalStmt.use.add(val);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    //不需要计算phi指令的in和out,因为use在前驱块的最后，计算活跃区间时只要考虑其def
    //每个块最后一条指令的out要去掉后继块的phi的def,否则会被一直传递到最开始
    public void runLiveAnalysis(IRFuncDef func){
        HashMap<String,HashSet<String>>phidefs=new HashMap<>();//记录每个块中phi指令的def
        ArrayList<Instruction>exit=new ArrayList<>();
        GetPrepareWork(func,exit,phidefs);
        //通过不动点迭代，从出口开始倒序BFS遍历控制流图，直到一次完整迭代前后没有变动
        boolean changed = true;
        int cnt=0;
        while(changed){
            changed = false;
            cnt++;
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
                //去掉succ中phi的def
                if(instr instanceof Br){
                    if(((Br) instr).haveCondition){
                        new_out.removeAll(phidefs.get(((Br) instr).iftrue));
                        new_out.removeAll(phidefs.get(((Br) instr).iffalse));
                    }else{
                        new_out.removeAll(phidefs.get(((Br) instr).dest));
                    }
                }
                for(var succ:instr.succs){
                    if(succ instanceof Select){
                        if(((Select) succ).type==1){//&&
                            new_out.remove(((Select) succ).val1);
                        }else if(((Select) succ).type==2){//||
                            new_out.remove(((Select) succ).val2);
                        }
                    }
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
