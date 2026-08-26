package thomas;

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
     * Starts the Thomas application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        new Thomas().run();
    }
}
