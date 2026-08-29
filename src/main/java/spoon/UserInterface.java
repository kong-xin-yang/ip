package spoon;

import java.util.Scanner;

/**
 * Handles the user interface and display of Spoon.
 */
public class UserInterface {
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

    private final Scanner scanner;

    // Constructor
    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    // Methods for interacting with user input
    public String readCommand() {
        return scanner.nextLine();
    }

    // Methods for printing pre-defined texts
    public void printDivider() {
        System.out.println(DIVIDER);
    }

    public void printStart() {
        System.out.println(BANNER);
        printDivider();
        System.out.println(INTRODUCTION);
        printDivider();
    }

    public void printError(String message) {
        System.out.println(message);
    }

    // Methods for printing interactions with the task list
    public void showTaskList(TaskList tasks) {
        System.out.println(LIST_INTRODUCTION);
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void showAdded(Task task, int taskSize) {
        System.out.println(TASK_ADDED);
        System.out.println(task);
        System.out.printf((LIST_LENGTH) + "%n", taskSize);
    }

    public void showMarked(Task task) {
        System.out.println(MARK_COMPLETE);
        System.out.println(task);
    }

    public void showUnmarked(Task task) {
        System.out.println(MARK_INCOMPLETE);
        System.out.println(task);
    }

    public void showDeleted(Task task, int totalTasks) {
        System.out.println(DELETE_TASK);
        System.out.println(task);
        System.out.printf((LIST_LENGTH) + "%n", totalTasks);
    }

    // Methods for closing Spoon
    public void close() {
        scanner.close();
    }

    public void printSave() {
        System.out.println("Tasks saved! Ready for next time!");
    }

    public void printExit() {
        System.out.println(EXIT);
    }
}