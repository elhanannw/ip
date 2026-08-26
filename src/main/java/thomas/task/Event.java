package thomas.task;

import java.time.LocalDate;
import java.time.LocalTime;

import thomas.ThomasException;
import thomas.util.DateTimeUtil;

/**
 * Represents a task that occurs within a start and end date/time.
 */
public class Event extends Task {
    protected LocalDate fromDate;
    protected LocalTime fromTime;
    protected LocalDate toDate;
    protected LocalTime toTime;

    /**
     * Creates an event with the given description, start date/time, and end date/time.
     *
     * @param description Textual description of the task.
     * @param from Start date/time.
     * @param to End date/time.
     * @throws ThomasException If a date format is invalid or the end is before the start.
     */
    public Event(String description, String from, String to) throws ThomasException {
        super(description);
        DateTimeUtil.ParsedDateTime parsedFrom = DateTimeUtil.parseDateTime(from);
        DateTimeUtil.ParsedDateTime parsedTo = DateTimeUtil.parseDateTime(to);

        this.fromDate = parsedFrom.getDate();
        this.fromTime = parsedFrom.getTime();
        this.toDate = parsedTo.getDate();
        this.toTime = parsedTo.getTime();

        if (toDate.isBefore(fromDate)) {
            throw new ThomasException(" Brooo how can event end date be before start date?");
        }
    }

    public LocalDate getFromDate() {
        return this.fromDate;
    }

    public LocalTime getFromTime() {
        return this.fromTime;
    }

    public LocalDate getToDate() {
        return this.toDate;
    }

    public LocalTime getToTime() {
        return this.toTime;
    }

    /**
     * Returns a formatted string for file storage.
     *
     * @return Formatted string representing this event task.
     */
    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | "
                + DateTimeUtil.toStorageString(fromDate, fromTime)
                + " | "
                + DateTimeUtil.toStorageString(toDate, toTime);
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return !(date.isBefore(fromDate) || date.isAfter(toDate));
    }

    /**
     * Returns the status, description, start, and end date/time of this event.
     *
     * @return Status icon, description, start, and end date/time as a string.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + DateTimeUtil.toDisplayString(fromDate, fromTime)
                + " to: " + DateTimeUtil.toDisplayString(toDate, toTime) + ")";
    }
}
