package ASM;

import ASM.module.ASMFuncDef;
import ASM.module.ASMGlobalVarDef;
import ASM.module.ASMStringDef;

import java.util.ArrayList;

public class ASMProgram {
    public ArrayList<ASMFuncDef> funcs;
    public ArrayList<ASMGlobalVarDef> globalvars;
    public ArrayList<ASMStringDef> strs;
    public int select_cnt=0;//用于给select的标签

    public ASMProgram() {
        funcs = new ArrayList<>();
        globalvars = new ArrayList<>();
        strs = new ArrayList<>();
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\t.text\n");
        for(ASMFuncDef f : funcs){
            sb.append(f.toString());
        }
        sb.append("\n\n");
        sb.append("\t.data\n");
        for(ASMGlobalVarDef gv : globalvars){
            sb.append(gv.toString());
        }
        sb.append("\n\n");
        sb.append("\t.rodata\n");
        for(ASMStringDef sd : strs){
            sb.append(sd.toString());
        }
        return sb.toString();
    }

}
