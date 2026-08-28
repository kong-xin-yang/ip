package spoon;

/**
 * Represents a Deadline (subclass of Task) with additional field deadline.
 */
public class Deadline extends Task {
    private String deadline;

    // Constructor
    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    // Methods
    @Override
    public String format() {
        return "D | " + super.format() +
                " | " + deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() +
                " (by: " + deadline + ")";
    }
}
