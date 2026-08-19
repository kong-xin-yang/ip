public abstract class Task {

    // Symbol Definitions
    private static final String COMPLETED_SYMBOL = "[X] ";
    private static final String UNCOMPLETED_SYMBOL = "[ ] ";

    private String name;
    private boolean completed = false;

    // Constructor
    public Task(String name) {
        this.name = name;
    }

    // Methods
    public void complete() {
        this.completed = true;
    }

    public void uncomplete() {
        this.completed = false;
    }

    @Override
    public String toString() {
        String status = this.completed ? COMPLETED_SYMBOL : UNCOMPLETED_SYMBOL;
        return status + " " + this.name;
    }
}
