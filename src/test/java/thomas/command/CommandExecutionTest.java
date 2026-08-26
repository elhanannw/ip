package thomas.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import thomas.ThomasException;
import thomas.storage.Storage;
import thomas.task.Deadline;
import thomas.task.Event;
import thomas.task.TaskList;
import thomas.task.Todo;
import thomas.ui.Ui;

class CommandExecutionTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void todoCommand_execute_addsAndPersistsTodo() throws Exception {
        TaskList tasks = new TaskList();
        new TodoCommand("read book").execute(tasks, new Ui(), storage());

        assertEquals(1, tasks.size());
        assertInstanceOf(Todo.class, tasks.get(0));
        assertEquals("read book", tasks.get(0).getDescription());
        assertEquals(1, storage().load().size());
    }

    @Test
    void deadlineAndEventCommands_execute_addTasks() throws Exception {
        TaskList tasks = new TaskList();
        Storage storage = storage();

        new DeadlineCommand("submit", "2026-08-26").execute(tasks, new Ui(), storage);
        new EventCommand("meeting", "2026-08-26", "2026-08-27").execute(tasks, new Ui(), storage);

        assertInstanceOf(Deadline.class, tasks.get(0));
        assertInstanceOf(Event.class, tasks.get(1));
        assertEquals(2, storage.load().size());
    }

    @Test
    void markAndUnmarkCommands_execute_changeTaskStatus() throws Exception {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("finish"));
        Storage storage = storage();
        Parser parser = new Parser();

        new MarkCommand("1", parser).execute(tasks, new Ui(), storage);
        assertTrue(tasks.get(0).isDone());
        new UnmarkCommand("1", parser).execute(tasks, new Ui(), storage);
        assertFalse(tasks.get(0).isDone());
    }

    @Test
    void deleteCommand_execute_removesTask() throws Exception {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("remove"));

        new DeleteCommand("1", new Parser()).execute(tasks, new Ui(), storage());

        assertTrue(tasks.getTasks().isEmpty());
    }

    @Test
    void todoCommand_emptyDescription_throwsWithoutAddingTask() {
        TaskList tasks = new TaskList();

        assertThrows(ThomasException.class, () ->
                new TodoCommand("").execute(tasks, new Ui(), storage()));
        assertEquals(0, tasks.size());
    }

    @Test
    void exitCommand_isExit_returnsTrue() {
        assertTrue(new ExitCommand().isExit());
        assertFalse(new ListCommand().isExit());
        assertFalse(new UnknownCommand().isExit());
    }

    @Test
    void unknownCommand_execute_throwsException() {
        assertThrows(ThomasException.class, () ->
                new UnknownCommand().execute(new TaskList(), new Ui(), storage()));
    }

    private Storage storage() {
        return new Storage(temporaryDirectory.toString(), "tasks.txt");
    }
}
