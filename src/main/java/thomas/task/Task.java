package thomas.task;

import java.time.LocalDate;

/**
 * Represents a task in task list.
 * Task consists of a description and a completion status.
 * */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with specific description.
     * The task is initially set as not done.
     *
     * @param description The textual description of task.
     * */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns status icon representing task completion
     *
     * @return "X" if task completed, " " if incomplete.
     * */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks this task as completed.
     * */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as incomplete.
     * */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Checks whether this task is completed.
     *
     * @return {@code true} if the task is done
     */
    public boolean isDone() { return this.isDone; }

    /**
     * Retrieves task description
     *
     * @return The description String of the task.
     * */
    public String getDescription() {
        return this.description;
    }

    /**
     * Checks if this task occurs on the given date.
     * Base tasks without date/time return false.
     *
     * @param date The date to check against.
     * @return true if task happens on the date; otherwise false.
     */
    public boolean occursOn(LocalDate date) {
        return false;
    }

    /**
     * Converts task into a formatted string for file storage.
     *
     * @return Formatted string representation for disk storage.
     * */
    public String toFileFormat() {
        return (isDone ? "Y" : "N") + " | " + description;
    }

    /**
     * Prints status and description of task
     *
     * @return Status Icon and description as a string.
     * */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
