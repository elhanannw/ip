import java.util.Scanner;

/**
 * Starts Thomas, a small command-line task manager.
 * Users can add tasks to do, deadlines, events, list these tasks, and toggle completion statuses.
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

                if (command.equals("list")) {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i=0; i<taskNo; i+=1) {
                        System.out.println(" " + (i+1) + "." + tasks[i]);
                    }
                } else if (command.startsWith("mark ")) {
                    int index = Integer.parseInt(command.substring(5).trim()) - 1;
                    if (index >= 0 && index < taskNo) {
                        tasks[index].markAsDone();
                        System.out.println(" Nice! Task has been marked as done:");
                        System.out.println("   " + tasks[index]);
                    }
                } else if (command.startsWith("unmark ")) {
                    int index = Integer.parseInt(command.substring(7).trim()) - 1;
                    if (index >= 0 && index < taskNo) {
                        tasks[index].markAsNotDone();
                        System.out.println(" Ok, task has been marked as not done:");
                        System.out.println("   " + tasks[index]);
                    }
                } else if (command.startsWith("todo ")) {
                    if (taskNo >= tasks.length) {
                        System.out.println(" Oops, task list is full!");
                    } else {
                        String des = command.substring(5).trim();
                        Task t = new Todo(des);
                        tasks[taskNo++] = t;
                        printTaskAdded(t, taskNo);
                    }
                } else if (command.startsWith("deadline ")) {
                    if (taskNo >= tasks.length) {
                        System.out.println(" Oops, task list is full!");
                    } else {
                        int byIndex = command.indexOf("/by");
                        if (byIndex == -1) {
                            System.out.println(" Oof, please specify the deadline using '/by <date>'.");
                        } else {
                            String des = command.substring(9, byIndex).trim();
                            String by = command.substring(byIndex + 3).trim();

                            Task t = new Deadline(des, by);
                            tasks[taskNo++] = t;
                            printTaskAdded(t, taskNo);
                        }
                    }
                } else if (command.startsWith("event ")) {
                    if (taskNo >= tasks.length) {
                        System.out.println(" Oops, task list is full!");
                    } else {
                        int fromIndex = command.indexOf("/from");
                        int toIndex = command.indexOf("/to");

                        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                            System.out.println(" Oof, please specify the event using 'event <desc> /from <start> /to <end>'.");
                        } else {
                            String des = command.substring(6, fromIndex).trim();
                            String from = command.substring(fromIndex + 5, toIndex).trim();
                            String to = command.substring(toIndex + 3).trim();

                            Task t = new Event(des, from, to);
                            tasks[taskNo++] = t;
                            printTaskAdded(t, taskNo);
                        }
                    }
                }
            }

            System.out.println(DIVIDER);
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