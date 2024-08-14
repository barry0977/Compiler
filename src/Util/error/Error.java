package Util.error;

import Util.Position;

public class Error extends RuntimeException {
    public String msg;
    public Position pos;

    public Error(String msg,Position pos) {
        this.msg = msg;
        this.pos = pos;
    }
}
