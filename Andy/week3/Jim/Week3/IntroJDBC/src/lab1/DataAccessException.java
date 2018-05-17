package lab1;

/**
 * An example of a custom exception class that could be used for all
 * database operations. 
 * 
 * @author jlombardo
 */
public class DataAccessException extends Exception {
    public DataAccessException(String msg) {
        super(msg);
    }
    
    public DataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
