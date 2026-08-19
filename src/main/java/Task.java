public class Task {
    private String name;
    private boolean completed = false;

    // Constructor
    public Task(String name) {
        this.name = name;
    }

    // Getter
    public String getName() {
        return this.name;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    // Methods
    public void complete() {
        this.completed = true;
    }

    public void uncomplete() {
        this.completed = false;
    }
}
