package spoon.command;

/**
 * Represents the set of valid comments recognized by Spoon.
 */
public enum Command {
    BYE,
    LIST,
    MARK,
    UNMARK,
    DELETE,
    ON,
    BY,
    TODO,
    DEADLINE,
    EVENT,
    UNKNOWN;

    public static Command fromString(String input) {
        if (input == null || input.isBlank()) {
            return UNKNOWN;
        }
        try {
            return Command.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
