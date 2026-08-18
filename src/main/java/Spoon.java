public class Spoon {
    public static void main(String[] args) {
        String divider = "-".repeat(60);
        String banner = "~~~ Welcome to Spoon ~~~";
        String introduction = "Hello, I'm Spoon, your friendly neighbourhood chatbot!\n" +
                "What do you wanna talk about?";
        String end = "Goodbye! Let's speak again soon!";

        System.out.println(banner);
        System.out.println(divider);
        System.out.println(introduction);
        System.out.println(divider);
        System.out.println(end);
    }
}
