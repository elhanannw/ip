package thomas.command;

import thomas.ThomasException;
import thomas.storage.Storage;
import thomas.task.Deadline;
import thomas.task.Task;
import thomas.task.TaskList;
import thomas.ui.Ui;

/**
 * Adds a deadline task to the task list.
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final String by;

    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ThomasException {
        Task task = new Deadline(description, by);
        tasks.add(task);
        storage.save(tasks);
        ui.showTaskAdded(task, tasks.size());
    }
}
