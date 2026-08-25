/**
 * Deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private final String argument;
    private final Parser parser;

    public DeleteCommand(String argument, Parser parser) {
        this.argument = argument;
        this.parser = parser;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ThomasException {
        int taskIndex = parser.getTaskIndex(argument, tasks.size(), "Pls specify task number to delete.");
        Task removedTask = tasks.delete(taskIndex);
        storage.save(tasks);
        ui.showTaskDeleted(removedTask, tasks.size());
    }
}
