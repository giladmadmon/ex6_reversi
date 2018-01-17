package reversi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import reversi.gui.PaneManager;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/
public class Main extends Application {
    private static final int MIN_STAGE_WIDTH = 470;
    private static final int MIN_STAGE_HEIGHT = 420;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            PaneManager paneManager = PaneManager.getInstance();
            primaryStage.setTitle("Reversi");
            primaryStage.setMinHeight(MIN_STAGE_HEIGHT);
            primaryStage.setMinWidth(MIN_STAGE_WIDTH);
            Scene scene = new Scene(paneManager.getMainMenu());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
