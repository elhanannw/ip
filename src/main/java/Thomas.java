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
        int taskNo = 0;

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                System.out.println(DIVIDER);

                if (command.trim().equals("bye")) {
                    System.out.println("Bye. See yaa!");
                    System.out.println(DIVIDER);
                    break;
                }

                if (command.equals("list")) {
                    for (int i=0; i<taskNo; i+=1) {
                        System.out.println(" " + (i+1) + ". " + tasks[i]);
                    }
                } else {
                    tasks[taskNo] = command;
                    taskNo+=1;
                    System.out.println(" added: " + command);
                }

                System.out.println(DIVIDER);
            }
        }
    }
}
