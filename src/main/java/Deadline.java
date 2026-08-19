/**
 * Represents a task with a deadline to be completed by.
 * */
public class Deadline extends Task {

    protected String by;

    /**
     * Constructs a new Deadline task with specific description and due date/time.
     *
     * @param description The textual description of task.
     * @param by The target date or time for completion.
     * */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Prints status, description and due date/time of deadline task
     *
     * @return Status icon, description, and due date/time as a string for the deadline task.
     * */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
