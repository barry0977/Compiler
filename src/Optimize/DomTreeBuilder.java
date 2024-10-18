package Optimize;

import IR.IRBlock;
import IR.IRProgram;
import IR.module.IRFuncDef;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;

public class DomTreeBuilder {
    public IRProgram program;

    //每个函数都要建一个支配树，因此每次进入一个新的函数要更新下面的内容
    public HashMap<IRBlock,Integer>index;//每个块在函数内的顺序
    public ArrayList<BitSet>Dom;//每个块的支配集

    public DomTreeBuilder(IRProgram program) {
        this.program = program;
    }


    //每个函数开始时，先初始化所有块的bitset的所有位为1，然后不断迭代
    public void init(IRFuncDef func){
        index = new HashMap<>();
        Dom = new ArrayList<>();
        int n = func.body.size()+1;
        int i=0;
        index.put(func.entry,i++);
        BitSet dom = new BitSet(n);
        dom.set(0);//entry只支配自己
        Dom.add(dom);
        for(var block:func.body){
            index.put(block,i++);
            dom = new BitSet(n);
            dom.set(0,n);
            Dom.add(dom);
        }

    }

    //构建支配树
    public void work(){
        for(var func:program.funcs){
            visitFunc(func);
        }
    }

    public void visitFunc(IRFuncDef func){
        init(func);
        ArrayList<IRBlock>RPO = ReversePostOrder(func);
        int n = RPO.size();
        //获得支配集
        boolean changed = true;
        while(changed){
            changed = false;
            for(var block:RPO){
                int ind = index.get(block);
                BitSet tmp = new BitSet(n);
                if(!block.label.equals("entry")){
                    tmp.set(0,n);//如果不是entry,先把所有位初始化为1
                }
                for(var pred:block.pred){
                    int predind = index.get(pred);
                    tmp.and(Dom.get(predind));//每个节点的支配集是所有前驱的支配集的交
                }
                tmp.set(ind);//每个块都支配自己
                if(!tmp.equals(Dom.get(ind))){//如果与原来不同，则发生变化，更新支配集并继续迭代
                    changed = true;
                    Dom.set(ind,tmp);
                }
            }
        }
        //获得直接支配节点/最近必经节点（IDom）
        //节点n的支配集中任意两个不同的节点，一个节点A必然为另一个节点B的必经节点。支配集中所有元素构成一条链，且直接支配节点的支配元素比当前节点少1
        //func.entry.idom = func.entry;
        for(var block:func.body){//entry没有IDom
            int ind = index.get(block);
            var dom = Dom.get(ind);//该节点的支配集
            int domindex = dom.nextSetBit(0);//获取被设置为1的位
            while(domindex != -1){
                if(Dom.get(domindex).cardinality() == (dom.cardinality() - 1)){
                    if(domindex == 0){
                        block.idom = func.entry;
                    }else{
                        block.idom = func.body.get(domindex - 1);
                    }
                    break;
                }
                domindex = dom.nextSetBit(domindex + 1);
            }
            block.idom.domChildren.add(block);//构建支配树，存在块内
        }
        //获得支配边界
        //一个节点X是节点Y的支配边界，当且仅当Y在X的一个控制流前驱节点的支配集中但Y不在X的支配集中。
        //n是且仅是(Dom(m)-(Dom(n)-{n}))的并中元素的支配边界，其中m是n的所有前驱
        for(var block:func.body){//entry没有前驱
            BitSet domFrontier = new BitSet(n);
            for(var pred:block.pred){
                int ind = index.get(block);
                int predind = index.get(pred);
                BitSet Dom_m = (BitSet) Dom.get(predind).clone();
                BitSet Dom_n = (BitSet) Dom.get(ind).clone();
                Dom_n.clear(ind);//去掉n
                Dom_m.andNot(Dom_n);
                domFrontier.or(Dom_m);//将所有集合并起来
            }
            int domindex = domFrontier.nextSetBit(0);
            while(domindex != -1){
                if(domindex == 0){//entry
                    func.entry.domFrontier.add(block);
                }else{
                    func.body.get(domindex - 1).domFrontier.add(block);
                }
                domindex = domFrontier.nextSetBit(domindex + 1);
            }
        }
    }

    //通过DFS获取CFG的逆后序
    public ArrayList<IRBlock> ReversePostOrder(IRFuncDef func){
        HashSet<IRBlock>visited = new HashSet<>();//在DFS中已经访问过的节点
        ArrayList<IRBlock> PostOrder = new ArrayList<>();//DFS后序遍历的结果
        IRBlock entry = func.entry;
        dfs(entry,visited,PostOrder);
        ArrayList<IRBlock>res = new ArrayList<>();
        for(int i = PostOrder.size()-1;i>=0;i--){//将后序遍历的结果逆转，即获得逆后序
            res.add(PostOrder.get(i));
        }
        return res;
    }

    public void dfs(IRBlock block,HashSet<IRBlock> visited,ArrayList<IRBlock>PostOrder){
        visited.add(block);
        for(var succ:block.succ){
            if(!visited.contains(succ)){
                dfs(succ,visited,PostOrder);
            }
        }
        PostOrder.add(block);//获得CFG后序访问的结果
    }
}
