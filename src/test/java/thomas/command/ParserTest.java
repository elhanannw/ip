package thomas.command;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import thomas.ThomasException;

class ParserTest {
    private final Parser parser = new Parser();

    @Test
    void getCommand_commandWithArguments_returnsCommandName() {
        assertEquals("deadline", parser.getCommand("deadline project /by 2026-08-26"));
        assertEquals("list", parser.getCommand("list"));
    }

    @Test
    void getArgument_commandPrefix_returnsTrimmedArgument() {
        assertEquals("finish report", parser.getArgument("todo   finish report", "todo"));
    }

    @Test
    void getTaskIndex_validOneBasedNumber_returnsZeroBasedIndex() throws ThomasException {
        assertEquals(1, parser.getTaskIndex("2", 3, "missing"));
    }

    @Test
    void getTaskIndex_emptyOrOutOfRange_throwsThomasException() {
        assertThrows(ThomasException.class, () -> parser.getTaskIndex("", 2, "missing"));
        assertThrows(ThomasException.class, () -> parser.getTaskIndex("0", 2, "missing"));
        assertThrows(ThomasException.class, () -> parser.getTaskIndex("3", 2, "missing"));
    }

    @Test
    void parse_supportedCommands_returnsMatchingCommandTypes() throws ThomasException {
        assertInstanceOf(ListCommand.class, parser.parse("list"));
        assertInstanceOf(TodoCommand.class, parser.parse("todo read book"));
        assertInstanceOf(DeadlineCommand.class, parser.parse("deadline submit /by 2026-08-26"));
        assertInstanceOf(EventCommand.class, parser.parse("event meeting /from 2026-08-26 /to 2026-08-27"));
        assertInstanceOf(OnCommand.class, parser.parse("on 2026-08-26"));
        assertInstanceOf(FindCommand.class, parser.parse("find book"));
        assertInstanceOf(MarkCommand.class, parser.parse("mark 1"));
        assertInstanceOf(UnmarkCommand.class, parser.parse("unmark 1"));
        assertInstanceOf(DeleteCommand.class, parser.parse("delete 1"));
        assertInstanceOf(ExitCommand.class, parser.parse("bye"));
        assertInstanceOf(UnknownCommand.class, parser.parse("unknown"));
    }

    @Test
    void getDeadlineDetails_validCommand_returnsDescriptionAndDate() throws ThomasException {
        assertArrayEquals(new String[]{"submit report", "2026-08-26 1800"},
                parser.getDeadlineDetails("deadline submit report /by 2026-08-26 1800"));
    }

    @Test
    void getDeadlineDetails_missingParts_throwsThomasException() {
        assertThrows(ThomasException.class, () -> parser.getDeadlineDetails("deadline submit report"));
        assertThrows(ThomasException.class, () -> parser.getDeadlineDetails("deadline /by 2026-08-26"));
        assertThrows(ThomasException.class, () -> parser.getDeadlineDetails("deadline submit /by"));
    }

    @Test
    void getEventDetails_validCommand_returnsDescriptionAndDates() throws ThomasException {
        assertArrayEquals(new String[]{"team meeting", "2026-08-26 0900", "2026-08-26 1000"},
                parser.getEventDetails("event team meeting /from 2026-08-26 0900 /to 2026-08-26 1000"));
    }

    @Test
    void getEventDetails_missingOrReversedParts_throwsThomasException() {
        assertThrows(ThomasException.class, () -> parser.getEventDetails("event meeting /from 2026-08-26"));
        assertThrows(ThomasException.class, () -> parser.getEventDetails("event meeting /from /to 2026-08-26"));
        assertThrows(ThomasException.class, () -> parser.getEventDetails("event meeting /to 2026-08-26 /from 2026-08-27"));
    }
}
