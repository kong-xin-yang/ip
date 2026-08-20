public class MissingArgumentException extends SpoonException {

    // Constructor
    public MissingArgumentException(String command, String argument) {
        super("Command " + command + " must have " + argument + ". Oops!");
    }
}
