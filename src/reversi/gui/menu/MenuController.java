package reversi.gui.menu;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import reversi.gui.PaneManager;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class MenuController extends VBox implements Initializable {
    private PaneManager paneManager;
    @FXML
    private Button settingsButton;

    /**
     * Initialize the menu.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        paneManager = PaneManager.getInstance();
    }

    /**
     * Load the game window.
     */
    @FXML
    public void loadGameWindow() {
        switchScene(paneManager.getGame());
    }

    /**
     * Load the settings window.
     */
    @FXML
    public void loadSettingsWindow() {
        switchScene(paneManager.getSettings());
    }

    /**
     * Exit game.
     */
    @FXML
    public void exitGame() {
        exit(0);
    }

    /**
     * Switch scene.
     * @param root the root.
     */
    private void switchScene(Pane root) {
        Scene scene = new Scene(root, settingsButton.getScene().getWidth(), settingsButton.getScene().getHeight());
        ((Stage) settingsButton.getScene().getWindow()).setScene(scene);
    }
}