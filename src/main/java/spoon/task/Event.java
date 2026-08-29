package spoon.task;

import spoon.exception.InvalidFormatException;
import spoon.exception.InvalidArgumentException;
import spoon.util.DateFormat;

/**
 * Represents an Event (subclass of Task) with additional fields startDate and endDate.
 */
public class Event extends Task {
    private final DateFormat.ParseResult startDate;
    private final DateFormat.ParseResult endDate;

    // Constructor
    public Event(String name, String startDate, String endDate) throws InvalidFormatException, InvalidArgumentException {
        super(name);
        this.startDate = DateFormat.parse(startDate);
        this.endDate = DateFormat.parse(endDate);
        if (this.startDate.dateTime().isAfter(this.endDate.dateTime())) {
            throw new InvalidArgumentException("Start time must be before end time!");
        }
    }

    // Methods
    @Override
    public String format() {
        return "E | " + super.format() +
                " | " + DateFormat.toStorage(startDate) +
                " | " + DateFormat.toStorage(endDate);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + DateFormat.toDisplay(startDate) +
                " to: " + DateFormat.toDisplay(endDate) + ")";
    }
}
