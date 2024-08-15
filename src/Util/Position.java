package Util;

import org.antlr.v4.runtime.ParserRuleContext;

public class Position {
    public int row;
    public int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position(ParserRuleContext ctx) {
        this.row = ctx.start.getLine();
        this.col = ctx.start.getCharPositionInLine();
    }

    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
