package IR;

import IR.module.IRClassDef;
import IR.module.IRFuncDef;
import IR.module.IRGlobalVarDef;

import java.util.ArrayList;
import java.util.HashMap;

public class IRProgram {
    public ArrayList<IRGlobalVarDef> globalvars;
    public ArrayList<IRClassDef>classs;
    public ArrayList<IRFuncDef>funcs;

    public IRProgram(){
        globalvars = new ArrayList<>();
        classs = new ArrayList<>();
        funcs = new ArrayList<>();
    }
}
