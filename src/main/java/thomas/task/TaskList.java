package thomas.task;

import java.util.ArrayList;
import thomas.ThomasException;

/**
 * Stores tasks and provides operations for changing the task list.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list from existing tasks.
     *
     * @param tasks tasks to store
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Appends a task to the end of the list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the given zero-based index.
     *
     * @param index position in the list
     * @return the task at that position
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes and returns the task at the given zero-based index.
     *
     * @param index position of the task to remove
     * @return the removed task
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns how many tasks are currently stored.
     *
     * @return task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return the stored tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Marks the task at {@code index} as done.
     *
     * @param index zero-based position of the task
     * @throws ThomasException if the task is already done
     */
    public void mark(int index) throws ThomasException {
        Task task = get(index);
        if (task.isDone()) {
            throw new ThomasException("Task is already marked as done!");
        }
        task.markAsDone();
    }

    /**
     * Marks the task at {@code index} as not done.
     *
     * @param index zero-based position of the task
     * @throws ThomasException if the task is already not done
     */
    public void unmark(int index) throws ThomasException {
        Task task = get(index);
        if (!task.isDone()) {
            throw new ThomasException("Task is already marked as not done!");
        }
        task.markAsNotDone();
    }
}