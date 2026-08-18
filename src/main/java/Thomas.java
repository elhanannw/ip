import java.util.Scanner;

/**
 * Starts Thomas, a small command-line program that repeats commands until the user says goodbye.
 */
public class Thomas {
    private static final String DIVIDER = "____________________________________________________________";
    /**
     * Displays a greeting, echoes each command, and exits when the user enters {@code bye}.
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

        try (Scanner scanner = new Scanner(System.in)) {
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(DIVIDER);

            if (command.trim().equals("bye")) {
                System.out.println("Bye. See yaa!");
                System.out.println(DIVIDER);
                break;
            }

            System.out.println(" " + command);
            System.out.println(DIVIDER);
            }
        }
    }
}
