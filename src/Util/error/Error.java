package Util.error;

import Util.Position;

public class Error extends RuntimeException {
    public String msg;
    public Position pos=null;

    public Error(String msg,Position pos) {
        this.msg = msg;
        this.pos = pos;
    }

    @Override
    public String toString(){
        return msg + " at " + pos.toString();
    }
}
