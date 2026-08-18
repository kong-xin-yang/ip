import java.util.Scanner;

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

        System.out.println(banner);
        System.out.println(divider);
        System.out.println(introduction);
        System.out.println(divider);

        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("bye")) {
            System.out.println(input);
            System.out.println(divider);
            input = scanner.nextLine();
        }

        System.out.println(end);
    }
}
