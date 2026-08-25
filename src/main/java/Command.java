/**
 * Represents one command that can be executed by Thomas.
 */
public abstract class Command {
    /**
     * Executes this command.
     *
     * @param tasks task list to change or inspect
     * @param ui user interface used for output
     * @param storage storage used to save changes
     * @throws ThomasException if the command cannot be completed
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws ThomasException;

    /**
     * Checks whether this command ends the application.
     *
     * @return true if the application should exit
     */
    public boolean isExit() {
        return false;
    }
}
