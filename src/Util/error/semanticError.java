package Util.error;

import Util.Position;

public class semanticError extends Error{
    public semanticError(String msg, Position pos){
        super(msg, pos);
    }
}
