package spoon;

/**
 * Represents a generic task with fields name and isCompleted.
 */
public abstract class Task {

    // Symbol Definitions
    private static final String COMPLETED_SYMBOL = "[X] ";
    private static final String UNCOMPLETED_SYMBOL = "[ ] ";

    private String name;
    private boolean isCompleted = false;

    // Constructor
    public Task(String name) {
        this.name = name;
    }

    // Methods
    public void complete() {
        this.isCompleted = true;
    }

    public void uncomplete() {
        this.isCompleted = false;
    }

    @Override
    public String toString() {
        String status = this.isCompleted ? COMPLETED_SYMBOL : UNCOMPLETED_SYMBOL;
        return status + " " + this.name;
    }
}
