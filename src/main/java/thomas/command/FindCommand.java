package thomas.command;

import java.util.ArrayList;

import thomas.ThomasException;
import thomas.storage.Storage;
import thomas.task.Task;
import thomas.task.TaskList;
import thomas.ui.Ui;

/**
 * Finds tasks whose descriptions contain a keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ThomasException {
        if (keyword.isEmpty()) {
            throw new ThomasException("broo, cannot find leh, provide a keyword eg: find book");
        }
        ArrayList<Task> matchingTasks = tasks.findByKeyword(keyword);
        ui.showMatchingTasks(matchingTasks);
    }
}
