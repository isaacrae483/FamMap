package Responce;

/**
 * error
 */

public class Error extends Response {
    String message;

    /**
     * constructor
     * @param e     String containing specific error
     */

    public Error(String e){
        message = e;
    }

    public String getMessage() {
        return message;
    }
}
