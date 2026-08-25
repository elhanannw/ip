import java.time.LocalDate;
import java.util.Scanner;

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
     * @param filePath folder and file path segments
     */
    public Thomas(String... filePath) {
        this.ui = new Ui();
        this.parser = new Parser();
        this.storage = new Storage(filePath);
        this.tasks = new TaskList(storage.load());
    }

    /**
     * Starts the command loop.
     */
    public void run() {
        ui.showWelcome();
        try (Scanner scanner = new Scanner(System.in)) {
            String command;
            while ((command = ui.readCommand(scanner)) != null) {
                ui.showDivider();
                if (command.equals("bye")) {
                    ui.showGoodbye();
                    break;
                }
                processCommand(command);
                ui.showDivider();
            }
        }
    }

    private void processCommand(String command) {
        try {
            String commandName = parser.getCommand(command);
            if (commandName.equals("list")) {
                ui.showTasks(tasks);
            } else if (commandName.equals("mark")) {
                int taskIndex = parser.getTaskIndex(parser.getArgument(command, "mark"), tasks.size(),
                        "Pls specify task number to mark.");
                tasks.mark(taskIndex);
                storage.save(tasks);
                ui.showTaskMarked(tasks.get(taskIndex));
            } else if (commandName.equals("unmark")) {
                int taskIndex = parser.getTaskIndex(parser.getArgument(command, "unmark"), tasks.size(),
                        "Pls specify task number to mark.");
                tasks.unmark(taskIndex);
                storage.save(tasks);
                ui.showTaskUnmarked(tasks.get(taskIndex));
            } else if (commandName.equals("delete")) {
                int taskIndex = parser.getTaskIndex(parser.getArgument(command, "delete"), tasks.size(),
                        "Pls specify task number to delete.");
                Task removedTask = tasks.delete(taskIndex);
                storage.save(tasks);
                ui.showTaskDeleted(removedTask, tasks.size());
            } else if (commandName.equals("todo")) {
                String description = parser.getArgument(command, "todo");
                if (description.isEmpty()) {
                    throw new ThomasException("Todo description cannot be empty.");
                }
                Task task = new Todo(description);
                tasks.add(task);
                storage.save(tasks);
                ui.showTaskAdded(task, tasks.size());
            } else if (commandName.equals("deadline")) {
                String[] details = parser.getDeadlineDetails(command);
                Task task = new Deadline(details[0], details[1]);
                tasks.add(task);
                storage.save(tasks);
                ui.showTaskAdded(task, tasks.size());
            } else if (commandName.equals("event")) {
                String[] details = parser.getEventDetails(command);
                Task task = new Event(details[0], details[1], details[2]);
                tasks.add(task);
                storage.save(tasks);
                ui.showTaskAdded(task, tasks.size());
            } else if (commandName.equals("on")) {
                String dateInput = parser.getArgument(command, "on");
                if (dateInput.isEmpty()) {
                    throw new ThomasException("Please provide a date. Eg: on 2026-08-09");
                }
                LocalDate targetDate = DateTimeUtil.parseDate(dateInput);
                ui.showTasksOn(tasks, targetDate);
            } else {
                throw new ThomasException("I doono what that is, try again mate :>");
            }
        } catch (ThomasException e) {
            ui.showError(e.getMessage());
        } catch (NumberFormatException e) {
            ui.showInvalidTaskNumber();
        }
    }

    /**
     * Thomas application main entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Thomas().run();
    }
}
