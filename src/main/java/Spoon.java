import java.util.Scanner;
import java.util.ArrayList;

public class Spoon {

    // String definitions
    private static final String DIVIDER = "-".repeat(60);
    private static final String BANNER = "~~~ Welcome to Spoon ~~~";
    private static final String INTRODUCTION = "Hello, I'm Spoon, your friendly neighbourhood chatbot!\n" +
            "What do you wanna talk about?";
    private static final String TASK_ADDED = "I've added this task to the list! :)";
    private static final String LIST_LENGTH = "Now, you have %d tasks!\n" +
            "You're doing great, keep up the good work!";
    private static final String LIST_INTRODUCTION = "Here's your list!";
    private static final String MARK_COMPLETE = "YAYYYY, task complete!";
    private static final String MARK_INCOMPLETE = "Oops, there's more work to be done!";
    private static final String NO_SUCH_COMMAND = "Uh oh, I don't know what that means";
    private static final String EXIT = "Goodbye! Let's speak again soon!";

    // Symbols
    private static final String TODO_SYMBOL = "[T]";
    private static final String DEADLINE_SYMBOL = "[D]";
    private static final String EVENT_SYMBOL = "[E]";
    private static final String COMPLETED_SYMBOL = "[X] ";
    private static final String UNCOMPLETED_SYMBOL = "[ ] ";

    // Scanner
    private final Scanner scanner;
    // List for storage
    private final ArrayList<Task> list;

    // Constructor
    public Spoon() {
        this.scanner = new Scanner(System.in);
        this.list = new ArrayList<>();
    }

    // TODO: determine if task is already completed or not when marking complete/incomplete
    public void run() {

        // Start message
        System.out.println(BANNER);
        System.out.println(DIVIDER);
        System.out.println(INTRODUCTION);
        System.out.println(DIVIDER);

        // Chat logic
        chatLoop: while (true) {
            // Get input and split it into commands and indexes
            String input = scanner.nextLine();
            String[] inputArray = input.split("\\s+", 2);
            String command = inputArray[0].toLowerCase();
            // Index only for mark and unmark commands
            String index = inputArray.length > 1 ? inputArray[1] : null;

            switch (command) {
                // Exit command
                case "bye": {
                    System.out.println(EXIT);
                    break chatLoop;
                }
                // List command
                case "list": {
                    System.out.println(LIST_INTRODUCTION);
                    for (int i = 1; i < list.size() + 1; i++) {
                        Task task = list.get(i - 1);
                        System.out.println(Integer.toString(i) + ". " + task.toString());
                    }
                    break;
                }
                // Mark command
                case "mark": {
                    Task task = list.get(Integer.parseInt(index) - 1);
                    task.complete();
                    System.out.println(MARK_COMPLETE);
                    System.out.println(task.toString());
                    break;
                }
                // Unmark command
                case "unmark": {
                    Task task = list.get(Integer.parseInt(index) - 1);
                    task.uncomplete();
                    System.out.println(MARK_INCOMPLETE);
                    System.out.println(task.toString());
                    break;
                }
                // Add todos, deadlines or events
                case "todo", "deadline", "event": {
                    Task task = switch (command) {
                        case "todo" -> new ToDo(input);
                        // TODO: Edit these
                        case "deadline" -> new Deadline(input, input);
                        case "event" -> new Event(input, input, input);
                        default -> null; // Cannot happen
                    };
                    list.add(task);
                    System.out.println(TASK_ADDED);
                    System.out.println(task.toString());
                    System.out.println(String.format(LIST_LENGTH, list.size()));
                }
                // Default: command not recognized
                default: {
                    System.out.println(NO_SUCH_COMMAND);
                }
            }
            System.out.println(DIVIDER);
        }

        // Clean up
        scanner.close();
    }

    // Entry point
    public static void main(String[] args) {
        new Spoon().run();
    }
}
