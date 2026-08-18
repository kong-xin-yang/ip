import java.util.Scanner;
import java.util.ArrayList;

public class Spoon {
    public static void main(String[] args) {
        // String definitions
        String divider = "-".repeat(60);
        String banner = "~~~ Welcome to Spoon ~~~";
        String introduction = "Hello, I'm Spoon, your friendly neighbourhood chatbot!\n" +
                "What do you wanna talk about?";
        String end = "Goodbye! Let's speak again soon!";

        // Scanner
        Scanner scanner = new Scanner(System.in);
        // List for storage
        ArrayList<String> list = new ArrayList<>();

        // Start message
        System.out.println(banner);
        System.out.println(divider);
        System.out.println(introduction);
        System.out.println(divider);

        // Chat logic
        chatLoop: while (true) {
            String input = scanner.nextLine();

            switch (input.toLowerCase()) {
                // Exit command
                case "bye":
                    System.out.println(end);
                    break chatLoop;
                // List command
                case "list":
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(Integer.toString(i + 1) + ". " + list.get(i));
                    }
                    System.out.println(divider);
                    break;
                default:
                    list.add(input);
                    System.out.println("Added " + input + " to list! :)");
                    System.out.println(divider);
            }
        }

        // Clean up
        scanner.close();
    }
}
