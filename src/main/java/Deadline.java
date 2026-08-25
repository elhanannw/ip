import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline to be completed by.
 * */
public class Deadline extends Task {

    protected LocalDate by;

    /**
     * Constructs a new Deadline task with specific description and due date/time.
     *
     * @param description The textual description of task.
     * @param by The target date or time for completion.
     * @throws ThomasException If date string format invalid.
     * */
    public Deadline(String description, String by) throws ThomasException {
        super(description);
        try {
            this.by = LocalDate.parse(by.trim());
        } catch (DateTimeParseException e) {
            throw new ThomasException("Wrong date format broooo! Use yyyy-MM-dd (eg: 2026-08-09).");
        }
    }

    /**
     * Gets parsed deadline date object.
     *
     * @return The LocalDate instance of the deadline.
     * */
    public LocalDate getBy() { return this.by; }

    /**
     * Converts Deadline task into a formatted string for text file storage.
     *
     * @return A formatted string representing Deadline task
     * */
    @Override
    public String toFileFormat() { return "D | " + super.toFileFormat() + " | " + by; }

    /**
     * Prints status, description and due date/time of deadline task
     *
     * @return Status icon, description, and due date/time as a string for the deadline task.
     * */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
