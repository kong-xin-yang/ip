package spoon.exception;

/**
 * Thrown when the Spoon chatbot executes logic it should never be able to.
 * Symbolizes a program breakdown.
 */
public class FatalErrorException extends SpoonException {

    // Constructor
    public FatalErrorException() {
        super("This ain't supposed to happen...\n" +
                "Something has gone terribly wrong!");
    }
}
