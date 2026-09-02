package thomas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import thomas.task.Task;
import thomas.task.TaskList;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Thomas thomas;

    private Image userImage = new Image(
            this.getClass().getResourceAsStream("/images/DaUser.jpg"));
    private Image thomasImage = new Image(
            this.getClass().getResourceAsStream("/images/DaBot.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Thomas instance.
     *
     * @param t The Thomas instance to use for command processing.
     */
    public void setThomas(Thomas t) {
        thomas = t;
        displayWelcome();
    }

    /**
     * Displays the welcome message and existing tasks.
     */
    private void displayWelcome() {
        String greeting = """
                ╔═══════════════════════════════╗
                ║  Welcome to Thomas!           ║
                ║  Your task manager chatbot    ║
                ╚═══════════════════════════════╝

                What can I do for you today?""";

        dialogContainer.getChildren().add(
                DialogBox.getThomasDialog(greeting, thomasImage)
        );

        // Display existing tasks
        TaskList tasks = thomas.getTasks();
        if (tasks.size() > 0) {
            StringBuilder taskList = new StringBuilder("Here are your existing tasks:\n");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                taskList.append("\n  ").append(i + 1).append(". ").append(task);
            }
            dialogContainer.getChildren().add(
                    DialogBox.getThomasDialog(taskList.toString(), thomasImage)
            );
        } else {
            dialogContainer.getChildren().add(
                    DialogBox.getThomasDialog(
                            "You don't have any tasks yet. "
                                    + "Type /help to see what you can do!",
                            thomasImage)
            );
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Thomas's reply and then appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty()) {
            return;
        }

        String response = thomas.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getThomasDialog(response, thomasImage)
        );
        userInput.clear();
    }
}
