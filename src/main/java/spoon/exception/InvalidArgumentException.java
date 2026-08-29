package spoon.exception;

public class InvalidArgumentException extends RuntimeException {

    // Constructor
    public InvalidArgumentException(String message) {
        super("That seems to be an error. " + message);
    }
}
