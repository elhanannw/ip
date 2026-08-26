package thomas.command;

import thomas.ThomasException;

/**
 * Parses command names and arguments entered by the user.
 */
public class Parser {
    /**
     * Converts a complete user command into an executable command object.
     *
     * @param fullCommand Complete user command.
     * @return Command represented by the input.
     * @throws ThomasException If required command parts are missing or invalid.
     */
    public Command parse(String fullCommand) throws ThomasException {
        String command = getCommand(fullCommand);
        if (command.equals("list")) {
            return new ListCommand();
        } else if (command.equals("mark")) {
            return new MarkCommand(getArgument(fullCommand, "mark"), this);
        } else if (command.equals("unmark")) {
            return new UnmarkCommand(getArgument(fullCommand, "unmark"), this);
        } else if (command.equals("delete")) {
            return new DeleteCommand(getArgument(fullCommand, "delete"), this);
        } else if (command.equals("todo")) {
            return new TodoCommand(getArgument(fullCommand, "todo"));
        } else if (command.equals("deadline")) {
            String[] details = getDeadlineDetails(fullCommand);
            return new DeadlineCommand(details[0], details[1]);
        } else if (command.equals("event")) {
            String[] details = getEventDetails(fullCommand);
            return new EventCommand(details[0], details[1], details[2]);
        } else if (command.equals("on")) {
            return new OnCommand(getArgument(fullCommand, "on"));
        } else if (command.equals("bye")) {
            return new ExitCommand();
        }
        return new UnknownCommand();
    }

    /**
     * Returns the first word of a command.
     *
     * @param command Complete user command.
     * @return Command name.
     */
    public String getCommand(String command) {
        int space = command.indexOf(' ');
        return space == -1 ? command : command.substring(0, space);
    }

    /**
     * Returns text following a command prefix.
     *
     * @param command Complete user command.
     * @param prefix Command prefix.
     * @return Trimmed argument text.
     */
    public String getArgument(String command, String prefix) {
        return command.substring(prefix.length()).trim();
    }

    /**
     * Parses a task number from a command argument.
     *
     * @param argument Task number argument.
     * @param taskCount Number of tasks in the list.
     * @param emptyMessage Error message when the argument is empty.
     * @return Zero-based task index.
     * @throws ThomasException If the argument is empty or outside the list.
     */
    public int getTaskIndex(String argument, int taskCount, String emptyMessage) throws ThomasException {
        if (argument.isEmpty()) {
            throw new ThomasException(emptyMessage);
        }
        int taskIndex = Integer.parseInt(argument) - 1;
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new ThomasException("Task number does not exist.");
        }
        return taskIndex;
    }

    /**
     * Extracts a deadline description and date/time.
     *
     * @param command Complete deadline command.
     * @return Description and date/time.
     * @throws ThomasException If required parts are absent.
     */
    public String[] getDeadlineDetails(String command) throws ThomasException {
        int byIndex = command.indexOf("/by");
        if (byIndex == -1) {
            throw new ThomasException("Deadline requires '/by <date>'. Eg: deadline Assignment 1 /by Tuesday");
        }
        String description = command.substring(8, byIndex).trim();
        String by = command.substring(byIndex + 3).trim();
        if (description.isEmpty()) {
            throw new ThomasException("Deadline description cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new ThomasException("Date/Time of '/by' cannot be empty.");
        }
        return new String[]{description, by};
    }

    /**
     * Extracts an event description, start, and end date/time.
     *
     * @param command Complete event command.
     * @return Description, start, and end date/time.
     * @throws ThomasException If required parts are absent.
     */
    public String[] getEventDetails(String command) throws ThomasException {
        int fromIndex = command.indexOf("/from");
        int toIndex = command.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            throw new ThomasException("Event requires '/from' and '/to'. Eg: event meeting /from Mon /to Thurs");
        }
        String description = command.substring(5, fromIndex).trim();
        String from = command.substring(fromIndex + 5, toIndex).trim();
        String to = command.substring(toIndex + 3).trim();
        if (description.isEmpty()) {
            throw new ThomasException("Event description cannot be empty.");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new ThomasException("Date/Time of '/from' or '/to' cannot be empty.");
        }
        return new String[]{description, from, to};
    }
}