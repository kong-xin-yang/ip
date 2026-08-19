public class Task {
    private String name;
    private boolean completed = false;

    public Task(String name) {
        this.name = name;
    }

    public void toggleCompleted() {
        this.completed = !this.completed;
    }
}
