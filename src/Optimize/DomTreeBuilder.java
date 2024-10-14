package Optimize;

import IR.IRBlock;
import IR.IRProgram;
import IR.module.IRFuncDef;

import java.util.ArrayList;
import java.util.HashSet;

public class DomTreeBuilder {
    public IRProgram program;
    public ArrayList<IRBlock>RPO;

    public DomTreeBuilder(IRProgram program) {
        this.program = program;
    }

    //构建支配树
    public void work(){
        for(var func:program.funcs){
            visitFunc(func);
        }
    }

    public void visitFunc(IRFuncDef func){

    }

    //通过DFS获取CFG的逆后序
    public void ReversePostOrder(IRFuncDef func){
        HashSet<IRBlock>visited = new HashSet<>();//在DFS中已经访问过的节点
        IRBlock entry = func.entry;
        dfs(entry,visited);
    }

    public void dfs(IRBlock block,HashSet<IRBlock> visited){
        visited.add(block);
        for(var succ:block.succ){
            if(!visited.contains(succ)){
                dfs(succ,visited);
            }
        }
        RPO.add(block);
    }
}
