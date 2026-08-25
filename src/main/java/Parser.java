/**
 * Parses command names and arguments entered by the user.
 */
public class Parser {
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