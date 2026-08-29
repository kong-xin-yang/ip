package spoon.exception;

public class InvalidArgumentException extends SpoonException {

    // Constructor
    public InvalidArgumentException(String message) {
        super("That seems to be an error. " + message);
    }
}
