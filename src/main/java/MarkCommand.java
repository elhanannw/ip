/**
 * Marks a task as done.
 */
public class MarkCommand extends Command {
    private final String argument;
    private final Parser parser;

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
