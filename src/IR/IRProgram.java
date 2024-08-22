package IR;

import IR.module.IRClassDef;
import IR.module.IRFuncDef;
import IR.module.IRGlobalVarDef;

import java.util.HashMap;

public class IRProgram {
    public HashMap<String, IRGlobalVarDef> globalvars;
    public HashMap<String, IRClassDef>classs;
    public HashMap<String, IRFuncDef>funcs;
}
