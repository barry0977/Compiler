package AST.Expr;

import AST.ASTVisitor;
import AST.Type.Type;
import Util.Position;

public class BinaryExprNode extends ExprNode {
    public ExprNode lhs,rhs;
    public enum binaryOpType{
        add,sub,mul,div,mod,lsf,rsf;
    }
    public binaryOpType opCode;

    public BinaryExprNode(ExprNode lhs, ExprNode rhs, binaryOpType opCode, Type type, Position pos){
        super(pos);
        this.lhs = lhs;
        this.rhs = rhs;
        this.opCode = opCode;
        this.type=type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
