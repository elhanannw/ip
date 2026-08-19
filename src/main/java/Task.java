/**
 * Represents a task in task list.
 * Task consists of a description and a completion status.
 * */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with specific description.
     *
     * @param description the textual description of task.
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
     * Marks task as completed.
     * */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks task as incomplete.
     * */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Retrieves task description
     *
     * @return Description String of the task.
     * */
    public String getDescription() {
        return this.description;
    }
}
