package spoon;

import spoon.exception.*;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * The root of the Spoon chatbot.
 * Main entry point + implementation logic.
 *
 * @author kongxinyang.
 */
public class Spoon {
    private static final String FILE_PATH = "./data/spoon.txt";

    // String definitions
    private static final String DIVIDER = "-".repeat(80);
    private static final String BANNER = "~~~ Welcome to Spoon ~~~";
    private static final String INTRODUCTION = "Hello, I'm Spoon, your friendly neighbourhood chatbot!"
            + System.lineSeparator() +
            "What do you wanna talk about?";
    private static final String TASK_ADDED = "I've added this task to the list! :)";
    private static final String LIST_LENGTH = "Now, you have %d task(s)! \uD83D\uDC4D";
    private static final String LIST_INTRODUCTION = "Here's your list!";
    private static final String MARK_COMPLETE = "YAYYYY, task complete!";
    private static final String MARK_INCOMPLETE = "Oops, there's more work to be done!";
    private static final String DELETE_TASK = "Okay, task deleted!";
    private static final String EXIT = "Goodbye! Let's speak again soon!";

    // Scanner
    private final Scanner scanner;
    // Storage
    private final Storage storage;
    // List of tasks
    private final ArrayList<Task> list;

    // Constructor
    public Spoon() {
        this.scanner = new Scanner(System.in);
        this.storage = new Storage(FILE_PATH);
        this.list = storage.load();
    }

    // Methods
    /**
     * Parses input and converts them to commands + arguments.
     *
     * @param input user input.
     * @throws SpoonException if an error is detected (refer to the below methods).
     * @throws InvalidCommandException if a command is invalid.
     */
    private void parseInput(String input) throws SpoonException{
        String[] inputArray = input.split("\\s+", 2);
        Command command = Command.fromString(inputArray[0]);

        switch (command) {
            // List command
            case LIST: {
                System.out.println(LIST_INTRODUCTION);
                for (int i = 1; i < list.size() + 1; i++) {
                    Task task = list.get(i - 1);
                    System.out.println(i + ". " + task.toString());
                }
                break;
            }

            // For mark, unmark and delete commands, inputArray[1] would be the index of the task
            // Mark, unmark and delete command
            case MARK, UNMARK, DELETE: {
                // Error handling
                int index = checkEdit(command, inputArray);
                Task task = list.get(index);

                switch (command) {
                    // Mark command
                    case MARK: {
                        task.complete();
                        System.out.println(MARK_COMPLETE);
                        break;
                    }
                    // Unmark command
                    case UNMARK: {
                        task.uncomplete();
                        System.out.println(MARK_INCOMPLETE);
                        break;
                    }
                    // Delete command
                    case DELETE: {
                        list.remove(index);
                        System.out.println(DELETE_TASK);
                        break;
                    }
                    // Default: placeholder value, should never happen
                    default: {
                        throw new FatalErrorException();
                    }
                }

                System.out.println(task);
                if (command == Command.DELETE) System.out.printf((LIST_LENGTH) + "%n", list.size());
                break;
            }

            // Add todos, deadlines or events command
            case TODO, DEADLINE, EVENT: {
                // Error handling
                Task task = checkAdd(command, inputArray);

                // Add command
                list.add(task);
                System.out.println(TASK_ADDED);
                System.out.println(task);
                System.out.printf((LIST_LENGTH) + "%n", list.size());
                break;
            }
            // Command.UNKNOWN and default: command not recognized
            case UNKNOWN:
                // Fall through
            default: {
                throw new InvalidCommandException();
            }
        }
    }

    /**
     * Checks for exceptions in mark, unmark and delete commands.
     *
     * @param command command parsed from user input.
     * @param inputArray arguments + inputs parsed from user input.
     * @return index of task with respect to list.
     * @throws InvalidFormatException if argument is not a number.
     * @throws IndexOutOfRangeException if argument is not within bounds.
     */
    private int checkEdit(Command command, String[] inputArray) throws SpoonException{
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
        if (index < 1 || index > list.size()) {
            throw new IndexOutOfRangeException(1, list.size());
        }

        return index - 1;
    }

    /**
     * Checks for exceptions in adding todos, deadlines and events.
     *
     * @param command command parsed from user input.
     * @param inputArray arguments + inputs parsed from user input.
     * @return task initialized with command.
     * @throws MissingArgumentException if argument(s) for task initialization is missing.
     * @throws FatalErrorException as a placeholder (should never happen).
     */
    private Task checkAdd(Command command, String[] inputArray) throws SpoonException{
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

    /**
     * Starts the Spoon chatbot.
     */
    public void run() {

        // Start message
        System.out.println(BANNER);
        System.out.println(DIVIDER);
        System.out.println(INTRODUCTION);
        System.out.println(DIVIDER);

        // Chat logic
        while (true) {
            // Get input and split it into commands, index and options
            String input = scanner.nextLine();
            // Exit command
            if (Command.fromString(input) == Command.BYE) {
                break;
            } else {
                try {
                    // Other commands
                    parseInput(input);
                } catch (SpoonException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println(DIVIDER);
        }

        // Save + clean up
        storage.save(list);
        scanner.close();
        System.out.println(EXIT);
    }

    // Entry point
    public static void main(String[] args) {
        new Spoon().run();
    }
}
