package spoon;

import java.io.IOException;
import java.time.LocalDate;

import spoon.command.Command;
import spoon.exception.*;
import spoon.parser.Parser;
import spoon.storage.Storage;
import spoon.task.Task;
import spoon.task.TaskList;
import spoon.ui.UserInterface;

/**
 * The root of the Spoon chatbot.
 * Main entry point + implementation logic.
 *
 * @author kongxinyang.
 */
public class Spoon {
    // File path to external file containing data
    private static final String FILE_PATH = "./data/spoon.txt";

    // User Interface
    private final UserInterface userInterface;
    // Storage
    private final Storage storage;
    // List of tasks
    private final TaskList tasks;

    // Constructor
    /**
     * Creates an instance of Spoon.
     */
    public Spoon() {
        this.userInterface = new UserInterface();
        this.storage = new Storage(FILE_PATH);

        TaskList tempTasks;
        try {
            tempTasks = new TaskList(storage.load());
            userInterface.printLoadSuccess();
        } catch (IOException e) {
            userInterface.printLoadingError(e.getMessage());
            tempTasks = new TaskList();
        } catch (SpoonException e) {
            userInterface.printError(e.getMessage());
            tempTasks = new TaskList();
        }
        this.tasks = tempTasks;

    }

    // Methods
    /**
     * Parses input and converts them to commands + arguments.
     *
     * @param input user input.
     * @throws SpoonException if an exception is detected (refer to the different exceptions).
     * @throws InvalidCommandException if a command is invalid.
     */
    private void executeCommand(String input) throws SpoonException {
        Command command = Parser.parseCommand(input);

        switch (command) {
            // List command
            case LIST: {
                userInterface.showTaskList(tasks);
                break;
            }

            // For mark, unmark and delete commands, inputArray[1] would be the index of the task
            // Mark, unmark and delete command
            case MARK, UNMARK, DELETE: {
                // Check for errors
                int index = Parser.checkEdit(input, tasks);
                Task task = tasks.get(index);

                switch (command) {
                    // Mark command
                    case MARK: {
                        task.complete();
                        userInterface.showMarked(task);
                        break;
                    }
                    // Unmark command
                    case UNMARK: {
                        task.uncomplete();
                        userInterface.showUnmarked(task);
                        break;
                    }
                    // Delete command
                    case DELETE: {
                        tasks.delete(index);
                        userInterface.showDeleted(task, tasks.size());
                        break;
                    }
                    // Default: placeholder value, should never happen
                    default: {
                        throw new FatalErrorException();
                    }
                }

                try {
                    storage.save(tasks.getTasks());
                } catch (IOException e) {
                    userInterface.printWritingError(e.getMessage());
                }
                break;
            }

            // Check for tasks on or by a specific date command
            case ON, BY: {
                // Check for errors
                LocalDate targetDate = Parser.checkDate(input);

                switch (command) {
                    // On command
                    case ON: {
                        TaskList filteredTasks = tasks.getTasksOn(targetDate);
                        userInterface.showFilteredTasks(filteredTasks, targetDate, Command.ON);
                        break;
                    }
                    // By command
                    case BY: {
                        TaskList filteredTasks = tasks.getTasksBy(targetDate);
                        userInterface.showFilteredTasks(filteredTasks, targetDate, Command.BY);
                        break;
                    }
                    // Default: placeholder value, should never happen
                    default: {
                        throw new FatalErrorException();
                    }
                }
                break;
            }

            // Filter all tasks that contain a word command
            case FIND: {
                String word = Parser.checkFind(input);
                TaskList filteredTasks = tasks.findTasks(word);
                userInterface.showFilteredTasks(filteredTasks, word);
                break;
            }

            // Add todos, deadlines or events command
            case TODO, DEADLINE, EVENT: {
                // Check for errors
                Task task = Parser.checkAdd(input);
                // Add command
                tasks.add(task);
                userInterface.showAdded(task, tasks.size());

                try {
                    storage.save(tasks.getTasks());
                } catch (IOException e) {
                    userInterface.printWritingError(e.getMessage());
                }
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
     * Wakes Spoon up! (starts the Spoon chatbot).
     */
    public void run() {

        // Start message
        userInterface.printStart();

        // Chat logic
        while (true) {
            // Get input and split it into commands, index and options
            String input = userInterface.readCommand();
            // Exit command
            if (Parser.parseCommand(input) == Command.BYE) {
                break;
            } else {
                try {
                    // Other commands
                    executeCommand(input);
                } catch (SpoonException e) {
                    userInterface.printError(e.getMessage());
                }
            }

            userInterface.printDivider();
        }

        // Save + clean up
        try {
            storage.save(tasks.getTasks());
            userInterface.printSave();
            userInterface.printExit();
            userInterface.close();
        } catch (IOException e) {
            userInterface.printWritingError(e.getMessage());
        }

    }

    // Entry point
    public static void main(String[] args) {
        new Spoon().run();
    }
}

// TODO: Add JUnit tests for the methods in all classes (so far, only Parser + DateFormat has been done)
