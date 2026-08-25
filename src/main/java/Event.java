import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that occurs within a specific timeframe (start and end date/time).
 * */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

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
        try {
            this.from = LocalDate.parse(from.trim());
            this.to = LocalDate.parse(to.trim());
        } catch (DateTimeParseException e) {
            throw new ThomasException("Wrong date format broooo! Use yyyy-MM-dd (eg: 2026-08-09).");
        }

    }

    /**
     * Retrieves the start date.
     *
     * @return The start LocalDate instance of the event.
     * */
    public LocalDate getFrom() { return this.from; }

    /**
     * retries the end date.
     *
     * @return The end LocalDate instance of the event.
     * */
    public LocalDate getTo() { return this.to; }

    /**
     * Converts Event task into a formatted string for text file storage.
     *
     * @return A formatted string representing Event task
     * */
    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " + from + " | " + to;
    }

    /**
     * Prints status, description, start, and end date/time of event task
     *
     * @return Status icon, description, start, and end date/time as a string for the event task.
     * */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + " to: "
                + to.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
