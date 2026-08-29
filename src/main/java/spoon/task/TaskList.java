package spoon.task;

import java.time.LocalDate;
import java.util.ArrayList;

import spoon.util.DateFormat;

/**
 * Handles the task list of Spoon.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    // Constructor
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
            // Safe to type cast here: task MUST be an instance of Deadline
            if (task instanceof Deadline &&
                    DateFormat.isDueOn(((Deadline) task).getDeadline().dateTime(), date)) {
                filteredTasks.add(task);
            // Safe to type cast here: task MUST be an instance of Event
            } else if (task instanceof Event && DateFormat.isOccurringOn(
                    ((Event) task).getStartDate().dateTime(),
                    ((Event) task).getEndDate().dateTime(), date)) {
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
            // Safe to type cast here: task MUST be an instance of Deadline (in the first case)
            // and an instance of Event (in the second case)
            if ((task instanceof Deadline &&
                    DateFormat.isDueBy(((Deadline) task).getDeadline().dateTime(), date)) ||
                    (task instanceof Event &&
                    DateFormat.isDueBy(((Event) task).getStartDate().dateTime(), date))) {
                filteredTasks.add(task);
            }
        }

        return new TaskList(filteredTasks);
    }

}