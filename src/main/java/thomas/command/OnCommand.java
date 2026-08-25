package thomas.command;

import java.time.LocalDate;
import thomas.ThomasException;
import thomas.storage.Storage;
import thomas.task.TaskList;
import thomas.ui.Ui;
import thomas.util.DateTimeUtil;

/**
 * Displays tasks that occur on a given date.
 */
public class OnCommand extends Command {
    private final String dateInput;

    public OnCommand(String dateInput) {
        this.dateInput = dateInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ThomasException {
        if (dateInput.isEmpty()) {
            throw new ThomasException("Please provide a date. Eg: on 2026-08-09");
        }
        LocalDate targetDate = DateTimeUtil.parseDate(dateInput);
        ui.showTasksOn(tasks, targetDate);
    }
}
