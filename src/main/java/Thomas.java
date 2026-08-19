import java.util.Scanner;

/**
 * Starts Thomas, a small command-line program that repeats commands until the user says goodbye.
 */
public class Thomas {
    private static final String DIVIDER = "____________________________________________________________";

    /**
     * Stores user commands in an array and lists upon request.
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

        String[] tasks = new String[100];
        boolean[] isDone = new boolean[100];
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
                    for (int i=0; i<taskNo; i+=1) {
                        String stateIcon = isDone[i] ? "[X]" : "[ ]";
                        System.out.println(" " + (i+1) + "." + stateIcon + " " + tasks[i]);
                    }
                } else if (command.startsWith("mark ")) {
                    int index = Integer.parseInt(command.substring(5).trim()) - 1;
                    if (index >= 0 && index < taskNo) {
                        isDone[index] = true;
                        System.out.println(" Nice! Task has been marked as done:");
                        System.out.println("   [X] " + tasks[index]);
                    }
                } else if (command.startsWith("unmark ")) {
                    int index = Integer.parseInt(command.substring(7).trim()) - 1;
                    if (index >= 0 && index < taskNo) {
                        isDone[index] = false;
                        System.out.println(" Ok, task has been marked as not done:");
                        System.out.println("   [ ] " + tasks[index]);
                    }
                }   else {
                    tasks[taskNo] = command;
                    isDone[taskNo] = false;
                    taskNo+=1;
                    System.out.println(" added: " + command);
                }

                System.out.println(DIVIDER);
            }
        }
    }
}
