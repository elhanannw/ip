package thomas.command;

import thomas.storage.Storage;
import thomas.task.TaskList;
import thomas.ui.Ui;

/**
 * Displays all tasks in the task list.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTasks(tasks);
    }
}
