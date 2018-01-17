package reversi.gui;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

import java.net.URL;

public class PaneManager {
    private static final String PANES_DIRECTORY = "resources/panes/";
    private static PaneManager paneManager;
    private PaneManager() {
    }
    /**
     * A 'singlton designed' constructor.
     *
     * @return the pane manger.
     */
    public static PaneManager getInstance() {
        if (paneManager == null) {
            paneManager = new PaneManager();
        }
        return paneManager;
    }
    /**
     * @return the main menu root pane.
     */
    public Pane getMainMenu() {
        return getPane("mainMenu", null);
    }
    /**
     * @return the game root pane.
     */
    public Pane getGame() {
        return getPane("game", null);
    }
    /**
     * @return the settings root pane.
     */
    public Pane getSettings() {
        return getPane("settings", null);
    }
    /**
     * @param containerFXML the name of the pane in the panes directory.
     * @param backgroundImg the background image of the pane.
     * @return the requested pane.
     */
    private Pane getPane(String containerFXML, String backgroundImg) {
        Pane pane = null;
        try {
            URL containerURL = getClass().getClassLoader().getResource(PANES_DIRECTORY + containerFXML + ".fxml");
            if (containerURL != null) {
                pane = FXMLLoader.load(containerURL);
                setBackground(pane, backgroundImg);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return pane;
    }
    /**
     * Set's background of a pane and make it stretch to fill pane.
     */
    public void setBackground(Pane root, String imgPath) {
        URL backgroundURL = getClass().getClassLoader().getResource("pictures/backgrounds/" + imgPath);
        if (imgPath != null && backgroundURL != null) {
            Image img = new Image(backgroundURL.toString());
            BackgroundSize size = new BackgroundSize(root.getWidth(), root.getHeight(), false, false, false, false);
            root.setBackground(new Background(new BackgroundImage(img, null, null, null, size)));
            root.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    BackgroundSize size = new BackgroundSize(root.getWidth(), root.getHeight(), false, false, false,
                                                             false);
                    root.setBackground(new Background(new BackgroundImage(img, null, null, null, size)));
                }
            });

            root.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    BackgroundSize size = new BackgroundSize(root.getWidth(), root.getHeight(), false, false, false,
                                                             false);
                    root.setBackground(new Background(new BackgroundImage(img, null, null, null, size)));
                }
            });
        }
    }
}
