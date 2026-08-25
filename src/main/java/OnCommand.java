import java.time.LocalDate;

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
