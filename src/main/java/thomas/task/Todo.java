package thomas.task;

/**
 * Represents todo task with no date or time.
 * */
public class Todo extends Task {

    /**
     * Constructs a Todo task with specific description.
     *
     * @param description The textual description of task.
     * */
    public Todo(String description) {
        super(description);
    }

    /**
     * Converts Todo task into a formatted string for text file storage.
     *
     * @return A formatted string representing Todo task
     * */
    @Override
    public String toFileFormat() { return "T | " + super.toFileFormat(); }

    /**
     * Prints status and description of todo task
     *
     * @return Status icon and description as a string for the todo task.
     * */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
