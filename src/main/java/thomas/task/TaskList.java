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

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void mark(int index) throws ThomasException {
        Task task = get(index);
        if (task.isDone()) {
            throw new ThomasException("Task is already marked as done!");
        }
        task.markAsDone();
    }

    public void unmark(int index) throws ThomasException {
        Task task = get(index);
        if (!task.isDone()) {
            throw new ThomasException("Task is already marked as not done!");
        }
        task.markAsNotDone();
    }

    /**
     * Returns all tasks with descriptions containing the given keyword.
     * Matching is case-insensitive.
     *
     * @param keyword text to find in task descriptions
     * @return matching tasks
     */
    public ArrayList<Task> findByKeyword(String keyword) {
        String normalizedKeyword = keyword.toLowerCase();
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(normalizedKeyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}