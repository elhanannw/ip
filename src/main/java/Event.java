import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a task that occurs within a specific timeframe (start and end date/time).
 * */
public class Event extends Task {
    protected LocalDate fromDate;
    protected LocalTime fromTime;
    protected LocalDate toDate;
    protected LocalTime toTime;

    /**
     * Constructs a new Event task with specific description, start date/time, and end date/time.
     *
     * @param description The textual description of task.
     * @param from The start date/time
     * @param to The end date/time
     * @throws ThomasException If either date format is invalid.
     * */
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

    /**
     * Retrieves the start date.
     *
     * @return The start LocalDate instance of the event.
     * */
    public LocalDate getFromDate() {
        return this.fromDate;
    }

    public LocalTime getFromTime() {
        return this.fromTime;
    }

    /**
     * retries the end date.
     *
     * @return The end LocalDate instance of the event.
     * */
    public LocalDate getToDate() {
        return this.toDate;
    }

    public LocalTime getToTime() {
        return this.toTime;
    }

    /**
     * Converts Event task into a formatted string for text file storage.
     *
     * @return A formatted string representing Event task
     * */
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
     * Prints status, description, start, and end date/time of event task
     *
     * @return Status icon, description, start, and end date/time as a string for the event task.
     * */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + DateTimeUtil.toDisplayString(fromDate, fromTime)
                + " to: " + DateTimeUtil.toDisplayString(toDate, toTime) + ")";
    }
}
