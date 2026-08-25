import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading tasks from and saving tasks to a hard disk file using OS-independent paths.
 * */
public class Storage {
    private final Path filePath;

    /**
     * Constructs a storage instance with OS-independent relative path.
     *
     * @param relativePath Folder path segment and file name.
     * */
    public Storage(String... relativePath) {
        this.filePath = Paths.get("", relativePath);
    }

    /**
     * Loads tasks from disk file
     *
     * @return ArrayList of restored task objects.
     * */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = filePath.toFile();

        // If directory or file doesn't exist
        if (!file.exists()) {
            try {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(" !!! Could not create data file, starting empty list");
            }
            return tasks;
        }
        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    Task task = parseLineToTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println(" !!! Corrupted data on line " + lineNumber + " skipped.");
                }
            }
        } catch (IOException e) {
            System.out.println(" Error reading storage file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Parses a single file line into corresponding Task object.
     *
     * @param line A single line from storage file.
     * @return The parsed task object.
     * @throws ThomasException if invalid or corrupt.
     * */
    private Task parseLineToTask(String line) throws ThomasException {
        int first = line.indexOf('|');
        if (first == -1) {
            throw new ThomasException("Invalid format: missing first");
        }

        int second = line.indexOf('|', first + 1);
        if (second == -1) {
            throw new ThomasException("Invalid format: missing second");
        }

        // Extract base task elements
        String type = line.substring(0, first).trim();
        boolean isDone = line.substring(first + 1, second).trim().equals("Y");

        Task task;

        if (type.equals("T")) {
            String description = line.substring(second + 1).trim();
            if (description.isEmpty()) {
                throw new ThomasException("Todo description empty!");
            }
            task = new Todo(description);

        } else if (type.equals("D")) {
            int third = line.indexOf('|', second + 1);
            if (third == -1) {
                throw new ThomasException("Missing deadline date/time");
            }

            String description = line.substring(second + 1, third).trim();
            String by = line.substring(third + 1).trim();

            if (description.isEmpty() || by.isEmpty()) {
                throw new ThomasException("Deadline description or date/time empty!");
            }

            task = new Deadline(description, by);

        } else if (type.equals("E")) {
            int third = line.indexOf('|', second + 1);
            if (third == -1) {
                throw new ThomasException("Missing event start time");
            }

            int fourth = line.indexOf('|', third + 1);
            String description = line.substring(second + 1, third).trim();
            String from;
            String to;

            if (fourth == -1) {
                from = line.substring(third + 1).trim();
                to = "";
            } else {
                from = line.substring(third + 1, fourth).trim();
                to = line.substring(fourth + 1).trim();
            }

            if (description.isEmpty() || from.isEmpty()) {
                throw new ThomasException("Event description or time empty!");
            }
            task = new Event(description, from, to);
        } else {
            throw new ThomasException("Broo, unknown task type" + type);
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Saves current list of tasks to disk file.
     *
     * @param tasks ArrayList of tasks to save.
     * */
    public void save(ArrayList<Task> tasks) {
        File file = filePath.toFile();
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try (FileWriter fw = new FileWriter(file)) {
                for (Task task : tasks) {
                    fw.write(task.toFileFormat() + System.lineSeparator());}
            }
        } catch (Exception e) {
            System.out.println(" !!! cannot save to disk: " + e.getMessage());
        }
    }
}



