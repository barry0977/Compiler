package IR;

import IR.instr.*;

import java.util.ArrayList;
import java.util.HashMap;

public class IRBlock extends IRNode {
    public String label;
    public ArrayList<Instruction> statements;
    public Instruction terminalStmt=null;//块内最后的语句，一定是ret或者br

    public HashMap<String,String>def2use;//块内def和use的映射,保存最近一次指针被def的值

    //CFG中的前驱和后继
    public ArrayList<IRBlock>pred=null,succ=null;

    //支配树
    public IRBlock idom = null;//直接支配节点
    public ArrayList<IRBlock>domChildren;//支配树上的子节点
    public ArrayList<IRBlock> domFrontier;//支配边界

    //phi放置
    public HashMap<String,Phi>philist;

    public IRBlock(String label) {
        this.label = label;
        statements = new ArrayList<>();
        def2use = new HashMap<>();
        pred = new ArrayList<>();
        succ = new ArrayList<>();
        domChildren = new ArrayList<>();
        domFrontier = new ArrayList<>();
        philist = new HashMap<>();
    }

    public void addIns(Instruction ins) {
        if(label.equals("entry")){
            if(terminalStmt == null){
                if((ins instanceof Br)||(ins instanceof Ret)){
                    terminalStmt=ins;
                }else{
                    statements.add(ins);
                }
            }
            else{
                if(!(ins instanceof Br||ins instanceof Ret)){
                    statements.add(ins);
                }
            }
        }else{
            if(terminalStmt==null){
                if((ins instanceof Br)||(ins instanceof Ret)){
                    terminalStmt=ins;
                }else{
                    statements.add(ins);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(label).append(":\n");
        for(var phi:philist.values()){
            sb.append(phi.toString());
        }
        for (Instruction i : statements) {
            sb.append(i.toString());
        }
        if(terminalStmt!=null){
            sb.append(terminalStmt.toString());
        }
        return sb.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
