package spoon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import spoon.exception.SpoonException;
import spoon.exception.FileCorruptedException;

/**
 * Handles the loading and saving of the list of tasks to an external file.
 */
public class Storage {
    private final String filePath;

    // Constructor
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    // Methods
    /**
     * Loads tasks from the file
     *
     * @return list of tasks; returns an empty list if file doesn't exist.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        int lineCounter = 0;

        // If file does not exist, initialize an empty list
        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                // Get next line
                String line = scanner.nextLine().trim();
                lineCounter ++;

                try {
                    // Check for empty line
                    if (line.isEmpty()) {
                        throw new FileCorruptedException(lineCounter);
                    }

                    String[] inputArray = line.split(" \\s*\\|\\s*");

                    // Check for missing or wrong completed status and name
                    if (inputArray.length < 3 || inputArray[2].isBlank() ||
                            !(inputArray[1].equals("0") || inputArray[1].equals("1"))) {
                        throw new FileCorruptedException(lineCounter);
                    }

                    String taskType = inputArray[0];
                    boolean isCompleted = inputArray[1].equals("1");
                    String name = inputArray[2];

                    Task task;
                    switch (taskType) {
                        case "T":
                            task = new ToDo(name);
                            break;
                        case "D":
                            if (inputArray.length < 4 || inputArray[3].isBlank()) {
                                throw new FileCorruptedException(lineCounter);
                            } else {
                                task = new Deadline(name,inputArray[3]);
                            }
                            break;
                        case "E":
                            if (inputArray.length < 5 || inputArray[3].isBlank() || inputArray[4].isBlank()) {
                                throw new FileCorruptedException(lineCounter);
                            } else {
                                task = new Event(name,inputArray[3], inputArray[4]);
                            }
                            break;
                        default:
                            throw new FileCorruptedException(lineCounter);
                    }


                    if (isCompleted) {
                        task.complete();
                    }
                    tasks.add(task);

                } catch (SpoonException e) {
                    System.out.println(e.getMessage());
                }
            }

        // Check for error reading file
        } catch (IOException e) {
            System.out.println("Error reading storage file: " + e.getMessage());
        }

        System.out.println("Tasks loaded! Time to get to work!" + System.lineSeparator());
        return tasks;
    }

    /**
     * Saves the current list of tasks to the file.
     *
     * @param tasks current list of tasks.
     */
    public void save(ArrayList<Task> tasks) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        // Automatically create ./data/ folder if missing
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter fw = new FileWriter(file)) {
            for (Task task : tasks) {
                fw.write(task.format() + System.lineSeparator());
            }

        // Check for error writing to file
        } catch (IOException e) {
            System.out.println("Error writing to storage file: " + e.getMessage());
        }

        System.out.println("Tasks saved! Ready for next time!");
    }
}
