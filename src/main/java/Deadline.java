public class Deadline extends Task {
    private String deadline;

    // Constructor
    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    // Methods
    @Override
    public String toString() {
        return "[D]" + super.toString();
    }
}
