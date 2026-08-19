/**
 * Represents a task that occurs within a specific timeframe (start and end date/time).
 * */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs a new Event task with specific description, start date/time, and end date/time.
     *
     * @param description The textual description of task.
     * @param from The start date/time
     * @param to The end date/time
     * */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Prints status, description, start, and end date/time of event task
     *
     * @return Status icon, description, start, and end date/time as a string for the event task.
     * */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
