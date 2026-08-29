package spoon.task;

import spoon.exception.InvalidFormatException;
import spoon.util.DateFormat;

/**
 * Represents a Deadline (subclass of Task) with additional field deadline.
 */
public class Deadline extends Task {
    private final DateFormat.ParseResult deadline;

    // Constructor
    public Deadline(String name, String deadline) throws InvalidFormatException {
        super(name);
        this.deadline = DateFormat.parse(deadline);
    }

    // Methods
    @Override
    public String format() {
        return "D | " + super.format() +
                " | " + DateFormat.toStorage(deadline);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() +
                " (by: " + DateFormat.toDisplay(deadline) + ")";
    }
}
