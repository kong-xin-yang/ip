import java.util.Scanner;
import java.util.ArrayList;

public class Spoon {

    // String definitions
    private static final String DIVIDER = "-".repeat(60);
    private static final String BANNER = "~~~ Welcome to Spoon ~~~";
    private static final String INTRODUCTION = "Hello, I'm Spoon, your friendly neighbourhood chatbot!\n" +
            "What do you wanna talk about?";
    private static final String LIST_INTRODUCTION = "Here's your list!";
    private static final String MARK_COMPLETE = "YAYYYY, task complete!";
    private static final String MARK_INCOMPLETE = "Oops, there's more work to be done!";
    private static final String EXIT = "Goodbye! Let's speak again soon!";

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

    // Helper method to construct output
    public String getOutput(int index) {
        Task task = list.get(index - 1);
        // Generates index of the task (starting from 1)
        String output = Integer.toString(index) + ". ";
        // Ternary operator to decide if task is marked completed or not
        output += task.getCompleted() ? COMPLETED_SYMBOL : UNCOMPLETED_SYMBOL;
        // Loads the task name
        output += task.getName();
        return output;
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
                        String newLine = getOutput(i);
                        System.out.println(newLine);
                    }
                    break;
                }
                // Mark command
                case "mark": {
                    int i = Integer.parseInt(index);
                    list.get(i - 1).complete();
                    System.out.println(MARK_COMPLETE);
                    String newLine = getOutput(i);
                    System.out.println(newLine);
                    break;
                }
                // Unmark command
                case "unmark": {
                    int i = Integer.parseInt(index);
                    list.get((Integer.parseInt(index) - 1)).uncomplete();
                    System.out.println(MARK_INCOMPLETE);
                    String newLine = getOutput(i);
                    System.out.println(newLine);
                    break;
                }
                // Default: adding tasks
                default: {
                    list.add(new Task(input));
                    System.out.println("Added " + input + " to list! :)");
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
