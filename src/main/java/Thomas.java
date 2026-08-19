import java.awt.*;
import java.util.Scanner;

/**
 * Starts Thomas, a command-line task manager app.
 * Users can manage tasks, adding Todos, Deadlines, and Events,
 * list these tasks, and toggle completion statuses.
 */
public class Thomas {
    private static final String DIVIDER = "____________________________________________________________";
    /**
     * Thomas application main entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String banner = """
                ▀▀█▀▀ █  █ █▀▀█ █▀▄▀█ █▀▀█ █▀▀
                  █   █▀▀█ █  █ █ ▀ █ █▄▄█ ▀▀█
                  ▀   ▀  ▀ ▀▀▀▀ ▀   ▀ ▀  ▀ ▀▀▀
                """;
        System.out.println(DIVIDER);
        System.out.println(banner);
        System.out.println("Hello! I'm Thomas.");
        System.out.println("Whats up? What can I do for you?");
        System.out.println(DIVIDER);

        Task[] tasks = new Task[100];
        int taskNo = 0;

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine().trim();
                System.out.println(DIVIDER);

                if (command.trim().equals("bye")) {
                    System.out.println("Bye. See yaa!");
                    System.out.println(DIVIDER);
                    break;
                }

                try {
                    /* list */
                    if (command.equals("list")) {
                        System.out.println(" Here are the tasks in your list:");
                        for (int i=0; i<taskNo; i+=1) {
                            System.out.println(" " + (i+1) + "." + tasks[i]);
                        }

                    }
                    /* mark */
                    else if (command.startsWith("mark")) {
                        // Changed to string. If user key in anything other than number (eg: mark two), previously would've crashed
                        String strIndex = command.substring(4).trim();

                        // Check if mark command empty
                        if (strIndex.isEmpty()) {
                            throw new ThomasException("Pls specify task number to mark.");
                        }
                        int taskNum = Integer.parseInt(strIndex) - 1;
                        // Cannot be out of list
                        if (taskNum < 0 || taskNum >= taskNo) {
                            throw new ThomasException("Task number doesnt exist.");
                        }
                        tasks[taskNum].markAsDone();
                        System.out.println(" Nice! Task has been marked as done:");
                        System.out.println("   " + tasks[taskNum]);

                    }
                    /* unmark */
                    else if (command.startsWith("unmark")) {
                        // Changed to string. If user key in anything other than number (eg: mark two), previously would've crashed
                        String strIndex = command.substring(6).trim();

                        // Empty check
                        if (strIndex.isEmpty()) {
                            throw new ThomasException("Pls specify task number to mark.");
                        }
                        int taskNum = Integer.parseInt(strIndex) - 1;

                        // Cannot be out of list
                        if (taskNum < 0 || taskNum >= taskNo) {
                            throw new ThomasException("Task number doesnt exist.");
                        }
                        tasks[taskNum].markAsNotDone();
                        System.out.println(" Ok, task has been marked as not done:");
                        System.out.println("   " + tasks[taskNum]);

                    }
                    /* to do */
                    else if (command.startsWith("todo")) {
                        String des = command.substring(4).trim();
                        // Check if unmark command empty
                        if (des.isEmpty()) {
                            throw new ThomasException("Todo description cannot be empty.");
                        }
                        Task t = new Todo(des);
                        tasks[taskNo++] = t;
                        printTaskAdded(t, taskNo);

                    }
                    /* deadline */
                    else if (command.startsWith("deadline")) {
                        int byIndex = command.indexOf("/by");

                        // Check if /by exists
                        String des;
                        if (byIndex == -1) {
                            // if no /by, description is after 'deadline'
                            des = command.substring(8).trim();
                        } else {
                            // if have /by, description is before 'deadline'
                            des = command.substring(8, byIndex).trim();
                        }

                        // Empty check for description
                        if (des.isEmpty()) {
                            throw new ThomasException("Deadline description cannot be empty.");
                        }

                        // If keyword /by is missing
                        if (byIndex == -1) {
                            throw new ThomasException("Deadline requires '/by <date>'. Eg: deadline Assignment 1 /by Tuesday");
                        }
                        String by = command.substring(byIndex + 3).trim();

                        // Empty check for by
                        if (by.isEmpty()) {
                            throw new ThomasException("Date/Time of '/by' cannot be empty.");
                        }
                        Task t = new Deadline(des, by);
                        tasks[taskNo++] = t;
                        printTaskAdded(t, taskNo);

                    }
                    /* event */
                    else if (command.startsWith("event")) {
                        int fromIndex = command.indexOf("/from");
                        int toIndex = command.indexOf("/to");

                        // check if /from exists
                        String des;
                        if (fromIndex == -1) {
                            // if no /from, description is after 'event'
                            des = command.substring(5).trim();
                        } else {
                            // if have /from, description before 'event'
                            des = command.substring(5, fromIndex).trim();
                        }

                        // Empty check for description
                        if (des.isEmpty()) {
                            throw new ThomasException("Event description cannot be empty.");
                        }

                        // Check if keyword present and in right order
                        if (fromIndex == -1 || toIndex == -1) {
                            throw new ThomasException("Event requires '/from' and '/to'. Eg: event meeting /from Mon /to Thurs");
                        }

                        String from = command.substring(fromIndex + 5, toIndex).trim();
                        String to = command.substring(toIndex + 3).trim();

                        // Empty check for from and to
                        if (from.isEmpty() || to.isEmpty()) {
                            throw new ThomasException("Date/Time of '/from' or '/to' cannot be empty.");
                        }

                        Task t = new Event(des, from, to);
                        tasks[taskNo++] = t;
                        printTaskAdded(t, taskNo);
                    } else {
                        throw new ThomasException("I doono what that is, try again mate :>");
                    }
                } catch (ThomasException e) {
                    System.out.println("oooooooof :<< " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("oooooooof Please enter a valid task number." );
                }
                System.out.println(DIVIDER);
            }
        }
    }

    /***
     * Prints confirmation message after a task is added successfully to list.
     * Displays task detail and task count.
     *
     * @param task The task that was added.
     * @param totalTask The current total number of tasks.
     */
    private static void printTaskAdded(Task task, int totalTask) {
        System.out.println(" Got it, added this task:");
        System.out.println("   " + task);
        System.out.println(" You have " + totalTask + " tasks left in the list.");
    }
}