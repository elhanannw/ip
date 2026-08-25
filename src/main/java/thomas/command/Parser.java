package thomas.command;

import thomas.ThomasException;

/**
 * Parses command names and arguments entered by the user.
 */
public class Parser {
    /**
     * Converts a complete user command into an executable command object.
     *
     * @param fullCommand complete user command
     * @return command represented by the input
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
     * @param command complete user command
     * @return command name
     */
    public String getCommand(String command) {
        int space = command.indexOf(' ');
        return space == -1 ? command : command.substring(0, space);
    }

    /**
     * Returns text following a command prefix.
     *
     * @param command complete user command
     * @param prefix command prefix
     * @return trimmed argument text
     */
    public String getArgument(String command, String prefix) {
        return command.substring(prefix.length()).trim();
    }

    /**
     * Parses a task number from a command argument.
     *
     * @param argument task number argument
     * @return zero-based task index
     * @throws ThomasException if the argument is empty or outside the list
     */
    public int getTaskIndex(String argument, int taskCount, String emptyMessage) throws ThomasException {
        if (argument.isEmpty()) {
            throw new ThomasException(emptyMessage);
        }
        int taskIndex = Integer.parseInt(argument) - 1;
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new ThomasException("Task number doesnt exist.");
        }
        return taskIndex;
    }

    /**
     * Extracts a deadline description and date/time.
     *
     * @param command complete deadline command
     * @return description and date/time
     * @throws ThomasException if required parts are absent
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
     * @param command complete event command
     * @return description, start, and end date/time
     * @throws ThomasException if required parts are absent
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