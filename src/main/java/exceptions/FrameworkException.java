package exceptions;

public class FrameworkException extends RuntimeException {

    /**
     * Pass the message that needs to be appended to the stacktrace
     *
     * @param message Details about the exception or custom message
     */
    public FrameworkException(String message) {
        super(message);
    }


}