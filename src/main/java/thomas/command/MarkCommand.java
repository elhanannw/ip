package thomas.command;

import thomas.ThomasException;
import thomas.storage.Storage;
import thomas.task.TaskList;
import thomas.ui.Ui;

/**
 * Marks a task as done.
 */
public class MarkCommand extends Command {
    private final String argument;
    private final Parser parser;

    /**
     * Creates a command that marks a task as done.
     *
     * @param argument Task number entered by the user.
     * @param parser Parser used to read the task index.
     */
    public MarkCommand(String argument, Parser parser) {
        this.argument = argument;
        this.parser = parser;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ThomasException {
        int taskIndex = parser.getTaskIndex(argument, tasks.size(), "Pls specify task number to mark.");
        tasks.mark(taskIndex);
        storage.save(tasks);
        ui.showTaskMarked(tasks.get(taskIndex));
    }
}
