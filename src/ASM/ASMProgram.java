package ASM;

import ASM.module.ASMFuncDef;
import ASM.module.ASMGlobalVarDef;
import ASM.module.ASMStringDef;

import java.util.ArrayList;

public class ASMProgram {
    public ArrayList<ASMFuncDef> funcs;
    public ArrayList<ASMGlobalVarDef> globalvars;
    public ArrayList<ASMStringDef> strs;

    public ASMProgram() {
        funcs = new ArrayList<>();
        globalvars = new ArrayList<>();
        strs = new ArrayList<>();
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

}
