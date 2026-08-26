package thomas.task;

import java.time.LocalDate;
import java.time.LocalTime;
import thomas.ThomasException;
import thomas.util.DateTimeUtil;

/**
 * Represents a task with a deadline to be completed by.
 * */
public class Deadline extends Task {

    protected LocalDate byDate;
    protected LocalTime byTime;

    /**
     * Constructs a new Deadline task with specific description and due date/time.
     *
     * @param description The textual description of task.
     * @param by The target date or time for completion.
     * @throws ThomasException If date string format invalid.
     * */
    public Deadline(String description, String by) throws ThomasException {
        super(description);
        DateTimeUtil.ParsedDateTime parsed = DateTimeUtil.parseDateTime(by);
        this.byDate = parsed.getDate();
        this.byTime = parsed.getTime();
    }

    /**
     * Gets parsed deadline date object.
     *
     * @return The LocalDate instance of the deadline.
     * */
    public LocalDate getByDate() {
        return this.byDate;
    }

    public LocalTime getByTime() {
        return this.byTime;
    }

    /**
     * Converts Deadline task into a formatted string for text file storage.
     *
     * @return A formatted string representing Deadline task
     * */
    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat() + " | " + DateTimeUtil.toStorageString(byDate, byTime);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return byDate.equals(date);
    }

    /**
     * Prints status, description and due date/time of deadline task
     *
     * @return Status icon, description, and due date/time as a string for the deadline task.
     * */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.toDisplayString(byDate, byTime) + ")";
    }
}
