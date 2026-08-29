package spoon;

import spoon.exception.*;

/**
 * Handles the input parsing of Spoon.
 */
public class Parser {
    // Methods
    /**
     * Parses the inputArray from user input.
     *
     * @param input user input.
     * @return inputArray parsed from user input.
     */
    public static String[] parseInput(String input) {
        return input.split("\\s+", 2);
    }

    /**
     * Parses the command from user input.
     *
     * @param input user input.
     * @return command parsed from user input.
     */
    public static Command parseCommand(String input) {
        return Command.fromString(parseInput(input)[0]);
    }

    /**
     * Checks for exceptions in mark, unmark and delete commands.
     *
     * @param input user input.
     * @return index of task with respect to the list of tasks.
     * @throws MissingArgumentException if argument is missing.
     * @throws InvalidFormatException if argument is not a number.
     * @throws IndexOutOfRangeException if argument is not within bounds.
     */
    public static int checkEdit(String input, TaskList tasks) throws SpoonException{
        String[] inputArray = parseInput(input);
        Command command = parseCommand(input);

        if (inputArray.length < 2 || inputArray[1].isBlank()) {
            throw new MissingArgumentException(command.toString().toLowerCase(), "index");
        }
        int index;

        // Check that argument is a number
        try {
            index = Integer.parseInt(inputArray[1]);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("number");
        }

        // Check that argument is within bounds
        if (index < 1 || index > tasks.size()) {
            throw new IndexOutOfRangeException(1, tasks.size());
        }

        return index - 1;
    }

    /**
     * Checks for exceptions in adding todos, deadlines and events.
     *
     * @param input user input.
     * @return task initialized with command.
     * @throws MissingArgumentException if argument(s) for task initialization is missing.
     * @throws InvalidFormatException if argument(s) for task initialization are in the wrong datetime format.
     * @throws InvalidArgumentException if arguments for task initialization are invalid (i.e. end date before start date).
     * @throws FatalErrorException as a placeholder (should never happen).
     */
    public static Task checkAdd(String input) throws SpoonException {
        String[] inputArray = parseInput(input);
        Command command = parseCommand(input);
        String commandString = command.toString().toLowerCase();

        if (inputArray.length < 2 || inputArray[1].isBlank()) {
            throw new MissingArgumentException(commandString, "description");
        }
        switch (command) {
            // Add todos command
            case TODO: {
                return new ToDo(inputArray[1]);
            }

            // Add deadlines command
            case DEADLINE: {
                if (inputArray[1].startsWith("/by")) {
                    throw new MissingArgumentException(commandString, "description");
                }
                String[] deadlineArray = inputArray[1].split("\\s+/by\\s+", 2);
                if (deadlineArray.length < 2 || deadlineArray[1].isBlank()) {
                    throw new MissingArgumentException(commandString, "deadline (starting with /by)");
                }
                return new Deadline(deadlineArray[0], deadlineArray[1]);
            }

            // Add events command
            case EVENT: {
                if (inputArray[1].startsWith("/from") || inputArray[1].startsWith("/to")) {
                    throw new MissingArgumentException(commandString, "description");
                }
                String[] eventArray = inputArray[1].split("\\s+/from\\s+", 2);
                if (eventArray.length < 2 || eventArray[1].isBlank() || eventArray[1].startsWith("(?i)/to")) {
                    throw new MissingArgumentException(commandString, "start date (starting with /from)");
                }
                String[] eventArgsArray = eventArray[1].split("\\s+/to\\s+", 2);
                if (eventArgsArray.length < 2 || eventArgsArray[1].isBlank()) {
                    throw new MissingArgumentException(commandString, "end date (starting with /to)");
                }
                return new Event(eventArray[0], eventArgsArray[0], eventArgsArray[1]);
            }

            // Default: placeholder value, should never happen
            default: {
                throw new FatalErrorException();
            }
        }
    }
}
