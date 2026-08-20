public class InvalidFormatException extends SpoonException {

    // Constructor
    public InvalidFormatException(String format) {
        super("Oops, that appears to be the wrong format! Please enter a " + format + ".");
    }
}
