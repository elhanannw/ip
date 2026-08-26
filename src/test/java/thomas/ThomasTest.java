package thomas;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ThomasTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void run_byeCommand_showsWelcomeAndGoodbye() {
        java.io.InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream("bye\n".getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(captured));
        try {
            new Thomas(temporaryDirectory.toString(), "tasks.txt").run();
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        String output = captured.toString();
        assertTrue(output.contains("Hello! I'm Thomas."));
        assertTrue(output.contains("Bye. See yaa!"));
    }
}
