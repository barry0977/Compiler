package IR.instr;

import java.util.ArrayList;

public class Call extends Instruction {
    public String result;
    public String ResultType;
    public String FunctionName;
    public ArrayList<String> ArgsTy;
    public ArrayList<String> ArgsVal;

    public Call() {
        ArgsTy = new ArrayList<>();
        ArgsVal = new ArrayList<>();
    }

    public Call(String result, String ResultType, String FunctionName) {
        this.result = result;
        this.ResultType = ResultType;
        this.FunctionName = FunctionName;
        ArgsTy = new ArrayList<>();
        ArgsVal = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\t");
        if(ResultType.equals("void")){
            builder.append("call void @");
        }else{
            builder.append(result);
            builder.append(" = call ");
            builder.append(ResultType);
            builder.append(" @");
        }
        builder.append(FunctionName);
        builder.append("(");
        for(int i = 0; i < ArgsTy.size(); i++){
            if(i>0){
                builder.append(", ");
            }
            builder.append(ArgsTy.get(i));
            builder.append(" ");
            builder.append(ArgsVal.get(i));
        }
        builder.append(")\n");
        return builder.toString();
    }
}
