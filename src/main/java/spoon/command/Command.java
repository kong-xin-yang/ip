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
    FIND,
    TODO,
    DEADLINE,
    EVENT,
    UNKNOWN;

    /**
     * Converts an input to its corresponding command.
     *
     * @param input the user input.
     * @return the corresponding command.
     */
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
