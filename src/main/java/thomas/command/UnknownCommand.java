package thomas.command;

import thomas.ThomasException;
import thomas.storage.Storage;
import thomas.task.TaskList;
import thomas.ui.Ui;

/**
 * Represents a command that Thomas does not recognize.
 */
public class UnknownCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ThomasException {
        throw new ThomasException("I doono what that is, try again mate :>");
    }
}
