package thomas.command;

import thomas.ThomasException;
import thomas.storage.Storage;
import thomas.task.Event;
import thomas.task.Task;
import thomas.task.TaskList;
import thomas.ui.Ui;

/**
 * Adds an event task to the task list.
 */
public class EventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ThomasException {
        Task task = new Event(description, from, to);
        tasks.add(task);
        storage.save(tasks);
        ui.showTaskAdded(task, tasks.size());
    }
}
