package AST.Expr;

import AST.ASTVisitor;
import Util.Position;

public class BinaryExprNode extends ExprNode {
    public ExprNode lhs,rhs;
//    public enum binaryOpType{
//        add,sub,mul,div,mod,lsf,rsf,and,or,xor,eq,ne,les,gre,lese,gree
//    }
    public String opCode;

    public BinaryExprNode(Position pos){
        super(pos);
    }

//    public GetOp(String op){
//        if(op.equals('+')){
//            opCode = binaryOpType.add;
//        }else if(op.equals('-')){
//            opCode = binaryOpType.sub;
//        }else if(op.equals('*')){
//            opCode = binaryOpType.mul;
//        }else if(op.equals('/')){
//            opCode = binaryOpType.div;
//        }else if(op.equals('%')){
//            opCode = binaryOpType.mod;
//        }else if(op.equals('^')){
//            opCode = binaryOpType.xor;
//        }else if(op.equals('&')){
//            opCode = binaryOpType.and;
//        }else if(op.equals('|')){
//            opCode = binaryOpType.or;
//        }else if(op.equals('<')){
//            opCode = binaryOpType.les;
//        }else if(op.equals('>')){
//            opCode = binaryOpType.gre;
//        }else if(op.equals("<=")){
//            opCode = binaryOpType.le;
//        }
//    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
