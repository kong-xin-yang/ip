public class IndexOutOfRangeException extends SpoonException{

    // Constructor
    public IndexOutOfRangeException(int lowerBound, int upperBound) {
        super(String.format("Yikes! That appears to be out of range! Please enter a number between %d and %d.",
                lowerBound, upperBound));
    }
}
