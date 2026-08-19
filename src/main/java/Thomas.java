import java.util.Scanner;

/**
 * Starts Thomas, a small command-line task manager.
 * Users can add tasks, list tasks, and toggle completion statuses.
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
                        System.out.println(" " + (i+1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription());
                    }
                } else if (command.startsWith("mark ")) {
                    int index = Integer.parseInt(command.substring(5).trim()) - 1;
                    if (index >= 0 && index < taskNo) {
                        tasks[index].markAsDone();
                        System.out.println(" Nice! Task has been marked as done:");
                        System.out.println("   [" + tasks[index].getStatusIcon() + "] " + tasks[index].getDescription());
                    }
                } else if (command.startsWith("unmark ")) {
                    int index = Integer.parseInt(command.substring(7).trim()) - 1;
                    if (index >= 0 && index < taskNo) {
                        tasks[index].markAsNotDone();
                        System.out.println(" Ok, task has been marked as not done:");
                        System.out.println("   [" + tasks[index].getStatusIcon() + "] " + tasks[index].getDescription());
                    }
                }   else {
                    tasks[taskNo] = new Task(command);
                    taskNo+=1;
                    System.out.println(" added: " + command);
                }

                System.out.println(DIVIDER);
            }
        }
    }
}
