package Util.error;

public class Error extends RuntimeException {
    public String msg;

    public Error(String msg) {
        this.msg = msg;
    }
}
