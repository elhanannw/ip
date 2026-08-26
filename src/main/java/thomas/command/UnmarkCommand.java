package thomas.command;

import thomas.ThomasException;
import thomas.storage.Storage;
import thomas.task.TaskList;
import thomas.ui.Ui;

/**
 * Marks a task as not done.
 */
public class UnmarkCommand extends Command {
    private final String argument;
    private final Parser parser;

    /**
     * Creates a command that marks a task as not done.
     *
     * @param argument 1-based task number as typed by the user
     * @param parser parser used to convert the argument into an index
     */
    public UnmarkCommand(String argument, Parser parser) {
        this.argument = argument;
        this.parser = parser;
    }

    /**
     * Marks the requested task not done, saves the list, and confirms the change.
     *
     * @param tasks task list to update
     * @param ui user interface used for output
     * @param storage storage used to persist the change
     * @throws ThomasException if the task number is missing, invalid, or already not done
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ThomasException {
        int taskIndex = parser.getTaskIndex(argument, tasks.size(), "Pls specify task number to mark.");
        tasks.unmark(taskIndex);
        storage.save(tasks);
        ui.showTaskUnmarked(tasks.get(taskIndex));
    }
}
