package thomas.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import thomas.task.TaskList;
import thomas.task.Todo;

class UiTest {
    @Test
    void readCommand_returnsTrimmedInputAndNullAtEnd() {
        Ui ui = new Ui();
        Scanner scanner = new Scanner("  list  \n");

        assertEquals("list", ui.readCommand(scanner));
        assertNull(ui.readCommand(scanner));
    }

    @Test
    void showTasksOn_reportsMatchingTasksAndEmptyResults() {
        Ui ui = new Ui();
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        String output = captureOutput(() -> {
            ui.showTasks(tasks);
            ui.showTasksOn(tasks, LocalDate.of(2026, 8, 26));
        });

        assertTrue(output.contains("1.[T][ ] read book"));
        assertTrue(output.contains("Lucky broo, got nothing due on 2026-08-26!"));
    }

    @Test
    void messageMethods_printExpectedMessages() {
        Ui ui = new Ui();
        Todo todo = new Todo("read book");

        String output = captureOutput(() -> {
            ui.showWelcome();
            ui.showTaskAdded(todo, 1);
            ui.showTaskMarked(todo);
            ui.showTaskUnmarked(todo);
            ui.showTaskDeleted(todo, 0);
            ui.showError("bad command");
            ui.showInvalidTaskNumber();
            ui.showGoodbye();
            ui.showDivider();
        });

        assertTrue(output.contains("Hello! I'm Thomas."));
        assertTrue(output.contains("Got it, added this task:"));
        assertTrue(output.contains("Nice! Task has been marked as done:"));
        assertTrue(output.contains("Ok, task has been marked as not done:"));
        assertTrue(output.contains("Alright! This task has been deleted:"));
        assertTrue(output.contains("bad command"));
        assertTrue(output.contains("Please enter a valid task number."));
        assertTrue(output.contains("Bye. See yaa!"));
    }

    private String captureOutput(Runnable action) {
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
        try {
            action.run();
            return captured.toString();
        } finally {
            System.setOut(originalOutput);
        }
    }
}
