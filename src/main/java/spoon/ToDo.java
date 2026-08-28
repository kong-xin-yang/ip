package spoon;

/**
 * Represents a To-Do (subclass of Task).
 */
public class ToDo extends Task{

    // Constructor
    public ToDo(String name) {
        super(name);
    }

    // Methods
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
