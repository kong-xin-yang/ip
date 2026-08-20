import java.util.Scanner;
import java.util.ArrayList;

public class Spoon {

    // String definitions
    private static final String DIVIDER = "-".repeat(60);
    private static final String BANNER = "~~~ Welcome to Spoon ~~~";
    private static final String INTRODUCTION = "Hello, I'm Spoon, your friendly neighbourhood chatbot!\n" +
            "What do you wanna talk about?";
    private static final String TASK_ADDED = "I've added this task to the list! :)";
    private static final String LIST_LENGTH = "Now, you have %d task(s)! \uD83D\uDC4D";
    private static final String LIST_INTRODUCTION = "Here's your list!";
    private static final String MARK_COMPLETE = "YAYYYY, task complete!";
    private static final String MARK_INCOMPLETE = "Oops, there's more work to be done!";
    private static final String NO_SUCH_COMMAND = "Uh oh, I don't know what that means";
    private static final String EXIT = "Goodbye! Let's speak again soon!";

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
            // Get input and split it into commands, index and options
            String input = scanner.nextLine();
            String[] inputArray = input.split("\\s+", 2);
            String command = inputArray[0].toLowerCase();

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
                        System.out.println(i + ". " + task.toString());
                    }
                    break;
                }
                // For mark and unmark commands, inputArray[1] would be the index of the task
                // Mark command
                case "mark": {
                    Task task = list.get(Integer.parseInt(inputArray[1]) - 1);
                    task.complete();
                    System.out.println(MARK_COMPLETE);
                    System.out.println(task);
                    break;
                }
                // Unmark command
                case "unmark": {
                    Task task = list.get(Integer.parseInt(inputArray[1]) - 1);
                    task.uncomplete();
                    System.out.println(MARK_INCOMPLETE);
                    System.out.println(task);
                    break;
                }
                // Add todos, deadlines or events
                case "todo", "deadline", "event": {
                    Task task;
                    switch (command) {
                        case "todo": {
                            task = new ToDo(inputArray[1]);
                            break;
                        }
                        case "deadline": {
                            String[] deadlineArray = inputArray[1].split("\\s+/by\\s+", 2);
                            task = new Deadline(deadlineArray[0], deadlineArray[1]);
                            break;
                        }
                        case "event": {
                            String[] eventArray = inputArray[1].split("\\s+/from\\s+", 2);
                            String[] eventArgsArray = eventArray[1].split("\\s+/to\\s+", 2);
                            task = new Event(eventArray[0], eventArgsArray[0], eventArgsArray[1]);
                            break;
                        }
                        // Default: placeholder value, should never happen
                        default: {
                            task = null;
                        }
                    }
                    list.add(task);
                    System.out.println(TASK_ADDED);
                    System.out.println(task);
                    System.out.printf((LIST_LENGTH) + "%n", list.size());
                    break;
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
