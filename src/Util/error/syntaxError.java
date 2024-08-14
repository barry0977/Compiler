package Util.error;

import Util.Position;

public class syntaxError extends Error {
    public syntaxError(String msg, Position pos) {
        super(msg, pos);
    }
}
