package thomas.ui;

import java.util.Scanner;
import java.util.ArrayList;
import thomas.task.Task;
import thomas.task.TaskList;

/**
 * Handles interactions with the user through the command line.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";

    /**
     * Shows the welcome message when Thomas starts.
     */
    public void showWelcome() {
        String banner = """
                ▀▀█▀▀ █  █ █▀▀█ █▀▄▀█ █▀▀█ █▀▀
                  █   █▀▀█ █  █ █ ▀ █ █▄▄█ ▀▀█
                  ▀   ▀  ▀ ▀▀▀▀ ▀   ▀ ▀  ▀▀▀
                """;
        showDivider();
        System.out.println(banner);
        System.out.println("Hello! I'm Thomas.");
        System.out.println("Whats up? What can I do for you?");
        showDivider();
    }

    /**
     * Reads one command from the user.
     *
     * @param scanner scanner connected to user input
     * @return the next command, or null when input ends
     */
    public String readCommand(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }

    /**
     * Shows the divider used between user interactions.
     */
    public void showDivider() {
        System.out.println(DIVIDER);
    }

    /**
     * Shows the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Bye. See yaa!");
        showDivider();
    }

    /**
     * Displays all tasks in the list.
     *
     * @param tasks task list to display
     */
    public void showTasks(TaskList tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i += 1) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays tasks that match a keyword search.
     *
     * @param matchingTasks matching tasks to display
     */
    public void showMatchingTasks(ArrayList<Task> matchingTasks) {
        System.out.println(" Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i += 1) {
            System.out.println(" " + (i + 1) + "." + matchingTasks.get(i));
        }
        if (matchingTasks.isEmpty()) {
            System.out.println(" Broo, no matching tasks found.");
        }
    }

    /**
     * Displays tasks occurring on a particular date.
     *
     * @param tasks task list to search
     * @param date date being searched
     */
    public void showTasksOn(TaskList tasks, java.time.LocalDate date) {
        int shown = 0;
        System.out.println(" Here are tasks on " + date + ":");
        for (int i = 0; i < tasks.size(); i += 1) {
            if (tasks.get(i).occursOn(date)) {
                shown++;
                System.out.println(" " + shown + "." + tasks.get(i));
            }
        }
        if (shown == 0) {
            System.out.println(" Lucky broo, got nothing due on " + date + "!");
        }
    }

    /**
     * Displays the confirmation for a newly added task.
     *
     * @param task task that was added
     * @param totalTask current task count
     */
    public void showTaskAdded(Task task, int totalTask) {
        System.out.println(" Got it, added this task:");
        System.out.println("   " + task);
        System.out.println(" You have " + totalTask + " tasks left in the list.");
    }

    /**
     * Displays a task completion confirmation.
     *
     * @param task task that was marked
     */
    public void showTaskMarked(Task task) {
        System.out.println(" Nice! Task has been marked as done:");
        System.out.println("   " + task);
    }

    /**
     * Displays an incomplete-task confirmation.
     *
     * @param task task that was unmarked
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(" Ok, task has been marked as not done:");
        System.out.println("   " + task);
    }

    /**
     * Displays a deletion confirmation.
     *
     * @param task task that was deleted
     * @param remainingTasks number of tasks left
     */
    public void showTaskDeleted(Task task, int remainingTasks) {
        System.out.println(" Alright! This task has been deleted:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + remainingTasks + " tasks left");
    }

    /**
     * Displays a user-facing error.
     *
     * @param message error message
     */
    public void showError(String message) {
        System.out.println("oooooooof :<< " + message);
    }

    /**
     * Displays an invalid-number error.
     */
    public void showInvalidTaskNumber() {
        System.out.println("oooooooof Please enter a valid task number.");
    }
}