package Backend;

import IR.IRProgram;
import IR.module.IRFuncDef;

public class LiveAnalysis {
    public IRProgram program;

    public LiveAnalysis(IRProgram program) {
        this.program = program;
    }

    public void runLiveAnalysis(IRFuncDef func){
        //通过不动点迭代，从出口开始倒序BFS遍历控制流图，直到一次完整迭代前后没有变动
        boolean changed = true;
        while(changed){

        }
    }
}
