package thomas.command;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import thomas.ThomasException;

/**
 * Parses command names and arguments entered by the user.
 */
public class Parser {
    private static final Pattern PLACE_FLAG = Pattern.compile("\\s/(name|type|at|rating|price|visited|note)(?=\\s|$)");
    private static final Pattern ANY_FLAG = Pattern.compile("\\s/(\\S+)(?=\\s|$)");
    /**
     * Converts a complete user command into an executable command object.
     *
     * @param fullCommand Complete user command.
     * @return Command represented by the input.
     * @throws ThomasException If required command parts are missing or invalid.
     */
    public Command parse(String fullCommand) throws ThomasException {
        String command = getCommand(fullCommand);
        switch (command) {
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(getArgument(fullCommand, "mark"), this);
            case "unmark":
                return new UnmarkCommand(getArgument(fullCommand, "unmark"), this);
            case "delete":
                return new DeleteCommand(getArgument(fullCommand, "delete"), this);
            case "todo":
                return new TodoCommand(getArgument(fullCommand, "todo"));
            case "deadline":
                String[] deadlineDetails = getDeadlineDetails(fullCommand);
                return new DeadlineCommand(deadlineDetails[0], deadlineDetails[1]);
            case "event":
                String[] eventDetails = getEventDetails(fullCommand);
                return new EventCommand(eventDetails[0], eventDetails[1], eventDetails[2]);
            case "on":
                return new OnCommand(getArgument(fullCommand, "on"));
            case "find":
                return new FindCommand(getArgument(fullCommand, "find"));
            case "place":
                return parseAddPlace(fullCommand);
            case "listplace":
                requireNoArgument(fullCommand, "listplace");
                return new ListPlaceCommand();
            case "findplace":
                return new FindPlaceCommand(getArgument(fullCommand, "findplace"));
            case "editplace":
                return parseEditPlace(fullCommand);
            case "deleteplace":
                return new DeletePlaceCommand(parsePlaceIndex(getArgument(fullCommand, "deleteplace"),
                        "Please specify a place number to delete."));
            case "confirmdeleteplace":
                return new ConfirmDeletePlaceCommand(parsePlaceIndex(getArgument(fullCommand, "confirmdeleteplace"),
                        "Please specify a place number to confirm."));
            case "bye":
                return new ExitCommand();
            default:
                return new UnknownCommand();
        }
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
        assert command.startsWith(prefix) : "Command must start with its expected prefix";
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
        assert taskCount >= 0 : "Task count must not be negative";
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
            throw new ThomasException("Deadline requires '/by <date>'. E.g., deadline Assignment 1 /by Tuesday");
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
            throw new ThomasException("Event requires '/from' and '/to'. E.g., event meeting /from Mon /to Thurs");
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

    private Command parseAddPlace(String command) throws ThomasException {
        String argument = getArgument(command, "place");
        Map<String, String> fields = parsePlaceFields(argument);
        Matcher matcher = PLACE_FLAG.matcher(argument);
        String name = matcher.find() ? argument.substring(0, matcher.start()).trim() : argument;
        return new AddPlaceCommand(name, fields);
    }

    private Command parseEditPlace(String command) throws ThomasException {
        String argument = getArgument(command, "editplace");
        int firstSpace = argument.indexOf(' ');
        if (argument.isEmpty()) {
            throw new ThomasException("Please specify a place number to edit.");
        }
        if (firstSpace == -1) {
            throw new ThomasException("Edit place requires one field. E.g., editplace 1 /rating 5");
        }
        int index = parsePlaceIndex(argument.substring(0, firstSpace), "Please specify a place number to edit.");
        Map<String, String> fields = parsePlaceFields(argument.substring(firstSpace));
        if (fields.size() != 1) {
            throw new ThomasException("Edit place requires exactly one field. E.g., editplace 1 /rating 5");
        }
        Map.Entry<String, String> field = fields.entrySet().iterator().next();
        return new EditPlaceCommand(index, field.getKey(), field.getValue());
    }

    private Map<String, String> parsePlaceFields(String text) throws ThomasException {
        Matcher unknownMatcher = ANY_FLAG.matcher(text);
        while (unknownMatcher.find()) {
            String field = unknownMatcher.group(1);
            if (!isPlaceField(field)) {
                throw new ThomasException("Unknown place field: /" + field + ".");
            }
        }
        Map<String, String> fields = new HashMap<>();
        Matcher matcher = PLACE_FLAG.matcher(text);
        int valueStart = -1;
        String previousField = null;
        while (matcher.find()) {
            if (previousField != null) {
                String value = text.substring(valueStart, matcher.start()).trim();
                addPlaceField(fields, previousField, value);
            }
            previousField = matcher.group(1);
            valueStart = matcher.end();
        }
        if (previousField != null) {
            addPlaceField(fields, previousField, text.substring(valueStart).trim());
        }
        return fields;
    }

    private void addPlaceField(Map<String, String> fields, String field, String value) throws ThomasException {
        if (fields.containsKey(field)) {
            throw new ThomasException("Each place field may be provided only once.");
        }
        if (value.isEmpty()) {
            throw new ThomasException("Place details cannot be empty.");
        }
        fields.put(field, value);
    }

    private int parsePlaceIndex(String argument, String emptyMessage) throws ThomasException {
        if (argument.isEmpty()) {
            throw new ThomasException(emptyMessage);
        }
        try {
            int index = Integer.parseInt(argument);
            if (index < 1) {
                throw new NumberFormatException();
            }
            return index;
        } catch (NumberFormatException e) {
            throw new ThomasException("Place number does not exist.");
        }
    }

    private void requireNoArgument(String command, String prefix) throws ThomasException {
        if (!getArgument(command, prefix).isEmpty()) {
            throw new ThomasException("" + prefix + " does not take any arguments.");
        }
    }

    private boolean isPlaceField(String field) {
        return field.equals("name") || field.equals("type") || field.equals("at") || field.equals("rating")
                || field.equals("price") || field.equals("visited") || field.equals("note");
    }
}
