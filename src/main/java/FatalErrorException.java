public class FatalErrorException extends SpoonException{

    // Constructor
    public FatalErrorException() {
        super("This ain't supposed to happen...\n" +
                "Something has gone terribly wrong!");
    }
}
