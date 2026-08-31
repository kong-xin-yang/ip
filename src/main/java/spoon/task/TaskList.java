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
        tasks.add(task);
    }

    public void delete(int index) {
        tasks.remove(index);
    }

    public Task get(int index) {
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

    /**
     * Finds all tasks whose description contains the specified word.
     *
     * @param word the word to search for.
     * @return a TaskList of tasks matching the keyword.
     */
    public TaskList findTasks(String word) {
        // TODO: change after merge
        TaskList filteredTasks = new TaskList(new ArrayList<>());
        for (Task task : tasks) {
            if (task.containsWord(word)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

}