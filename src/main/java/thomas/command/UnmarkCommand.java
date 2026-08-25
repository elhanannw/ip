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

    public UnmarkCommand(String argument, Parser parser) {
        this.argument = argument;
        this.parser = parser;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ThomasException {
        int taskIndex = parser.getTaskIndex(argument, tasks.size(), "Pls specify task number to mark.");
        tasks.unmark(taskIndex);
        storage.save(tasks);
        ui.showTaskUnmarked(tasks.get(taskIndex));
    }
}
