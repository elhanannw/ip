package thomas;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import thomas.command.Command;
import thomas.command.Parser;
import thomas.storage.Storage;
import thomas.task.TaskList;
import thomas.ui.Ui;

/**
 * Starts Thomas, a command-line task manager app.
 * Users can manage tasks, adding Todos, Deadlines, and Events,
 * list these tasks, mark/unmark, delete, and with persistent disk storage.
 */
public class Thomas {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    /**
     * Creates Thomas and loads the saved tasks.
     */
    public Thomas() {
        this("data", "thomas.txt");
    }

    /**
     * Creates Thomas with a configurable storage path.
     *
     * @param filePath Folder and file path segments.
     */
    public Thomas(String... filePath) {
        this.ui = new Ui();
        this.parser = new Parser();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(storage.load());
    }

    /**
     * Starts the command loop until the user exits.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        try (Scanner scanner = new Scanner(System.in)) {
            while (!isExit) {
                try {
                    String fullCommand = ui.readCommand(scanner);
                    if (fullCommand == null) {
                        break;
                    }
                    ui.showDivider();
                    Command command = parser.parse(fullCommand);
                    command.execute(tasks, ui, storage);
                    isExit = command.isExit();
                } catch (ThomasException e) {
                    ui.showError(e.getMessage());
                } catch (NumberFormatException e) {
                    ui.showInvalidTaskNumber();
                } finally {
                    ui.showDivider();
                }
            }
        }
    }

    /**
     * Processes a command and returns the response as a string.
     * Used by the GUI to get responses without printing to System.out.
     *
     * @param userInput The command input from the user.
     * @return The response string.
     */
    public String getResponse(String userInput) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Command command = parser.parse(userInput);
            command.execute(tasks, ui, storage);
        } catch (ThomasException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid task number.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            System.setOut(originalOut);
        }

        String response = outputStream.toString();
        return response.isEmpty() ? "Command executed." : response;
    }

    /**
     * Returns the task list for GUI display.
     *
     * @return The current task list.
     */
    public TaskList getTasks() {
        return tasks;
    }

    /**
     * Starts the Thomas application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        new Thomas().run();
    }
}
