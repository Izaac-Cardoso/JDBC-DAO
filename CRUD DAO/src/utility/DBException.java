package utility;

public class DBException extends RuntimeException {

    private static long serialVersion = 1L;

    public DBException(String msg) {
        super(msg);
    }
}
