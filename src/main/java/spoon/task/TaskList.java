package spoon.task;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Handles the task list of Spoon.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    // Constructor
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks != null ? tasks : new ArrayList<>();
    }

    public void add(Task task) {
        // Task should never be null
        assert task != null : "A null task is added to TaskList";
        tasks.add(task);
    }

    public void delete(int index) {
        // Index should be within the bounds of the list
        assert index >= 0 && index < tasks.size() : "Index to delete is out of bounds";
        int initialSize = this.size();
        tasks.remove(index);
        // Size of TaskList should decrease by 1 after deletion
        assert tasks.size() == initialSize - 1 : "TaskList size did not decrease by 1 after deletion";
    }

    public Task get(int index) {
        // Index should be within the bounds of the list
        assert index >= 0 && index < tasks.size() : "Index to access is out of bounds";
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Filters tasks to those occurring on the date.
     *
     * @param date target date where tasks are happening.
     * @return a TaskList of tasks happening on the date.
     */
    public TaskList getTasksOn(LocalDate date) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isDueOn(date)) {
                filteredTasks.add(task);
            }
        }

        return new TaskList(filteredTasks);
    }

    /**
     * Filters tasks to those occurring by the date.
     *
     * @param date target date where tasks are happening.
     * @return a TaskList of tasks happening by the date.
     */
    public TaskList getTasksBy(LocalDate date) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isDueBy(date)) {
                filteredTasks.add(task);
            }
        }

        return new TaskList(filteredTasks);
    }

    // TODO: change the predicate method to streams and use it on find tasks too

    /**
     * Finds all tasks whose description contains the specified word.
     *
     * @param words an arbitrary number of words to search for.
     *              can accept zero or more arguments.
     * @return a TaskList of tasks matching the keyword.
     */
    public TaskList findTasks(String... words) {
        TaskList filteredTasks = new TaskList(new ArrayList<>());
        for (Task task : tasks) {
            for (String word : words) {
                if (task.containsWord(word)) {
                    filteredTasks.add(task);
                    break;
                }
            }
        }
        return filteredTasks;
    }
}

// TODO: Add Varargs implementation for getTasksOn and getTasksBy
