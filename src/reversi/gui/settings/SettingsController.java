package reversi.gui.settings;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import reversi.gui.PaneManager;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class SettingsController implements Initializable {
    private Settings settings;
    @FXML
    private ChoiceBox boardSize;
    @FXML
    private RadioButton startPlayerOne;
    @FXML
    private RadioButton startPlayerTwo;
    @FXML
    private ColorPicker colorPlayerOne;
    @FXML
    private ColorPicker colorPlayerTwo;
    @FXML
    private ComboBox<String> playerOnePicture;
    @FXML
    private ComboBox<String> playerTwoPicture;
    @FXML
    private RadioButton playerOneColorOpt;
    @FXML
    private RadioButton playerOnePictureOpt;
    @FXML
    private RadioButton playerTwoColorOpt;
    @FXML
    private RadioButton playerTwoPictureOpt;
    private Map<String, Image> playerImgs;
    /**
     * Initialize the settings.
     *
     * @param location
     * @param resources
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        settings = new Settings();

        // cache all the player images once
        playerImgs = new TreeMap<>();
        for (String playerImg : playerOnePicture.getItems()) {
            playerImgs.put(playerImg, new Image(
                    getClass().getClassLoader().getResource("pictures/players/" + playerImg).toString()));
        }

        // change the board size
        boardSize.getSelectionModel().select(Integer.toString(settings.getBoardSize()));

        // change the starting player
        if (settings.getStartingPlayer() == 1) {
            startPlayerOne.setSelected(true);
        } else {
            startPlayerTwo.setSelected(true);
        }

        // set default players pictures
        setPictureComboBox(playerOnePicture);
        setPictureComboBox(playerTwoPicture);

        // player one color/image choice
        if (settings.containDefinition(Settings.COLOR_1_DEF)) {
            colorPlayerOne.setValue(settings.getFirstColor());
            playerOneColorOpt.setSelected(true);
            firstPlayerNoImage();
            playerOnePicture.setValue("img1.png");
        } else {
            playerOnePicture.setValue(settings.getFirstPicture());
            playerOnePictureOpt.setSelected(true);
            firstPlayerNoColor();
        }

        // player two color/image choice
        if (settings.containDefinition(Settings.COLOR_2_DEF)) {
            colorPlayerTwo.setValue(settings.getSecondColor());
            playerTwoColorOpt.setSelected(true);
            secondPlayerNoImage();
            playerTwoPicture.setValue("img2.png");
        } else {
            playerTwoPicture.setValue(settings.getSecondPicture());
            playerTwoPictureOpt.setSelected(true);
            secondPlayerNoColor();
        }
    }
    /**
     * back to menu button from setting scene - saves the current settings.
     * replace the showing scene to the menu.
     */
    @FXML
    private void backToMenu() {
        updateSettings();

        try {
            PaneManager paneManager = PaneManager.getInstance();
            Scene scene = new Scene(paneManager.getMainMenu(), boardSize.getScene().getWidth(),
                                    boardSize.getScene().getHeight());
            ((Stage) boardSize.getScene().getWindow()).setScene(scene);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * update the current settings.
     */
    private void updateSettings() {
        // board size
        settings.setDefinition(Settings.BOARD_SIZE_DEF, boardSize.getValue().toString());

        // starting player
        if (startPlayerOne.selectedProperty().get()) {
            settings.setDefinition(Settings.STARTING_PLAYER_DEF, "1");
        } else {
            settings.setDefinition(Settings.STARTING_PLAYER_DEF, "2");
        }

        // player one color/image
        if (playerOneColorOpt.selectedProperty().get()) {
            settings.setDefinition(Settings.COLOR_1_DEF, colorPlayerOne.getValue().toString());
            settings.removeDefinition(Settings.IMAGE_1_DEF);
        } else {
            settings.setDefinition(Settings.IMAGE_1_DEF, playerOnePicture.getValue());
            settings.removeDefinition(Settings.COLOR_1_DEF);
        }

        // player two color/image
        if (playerTwoColorOpt.selectedProperty().get()) {
            settings.setDefinition(Settings.COLOR_2_DEF, colorPlayerTwo.getValue().toString());
            settings.removeDefinition(Settings.IMAGE_2_DEF);
        } else {
            settings.setDefinition(Settings.IMAGE_2_DEF, playerTwoPicture.getValue());
            settings.removeDefinition(Settings.COLOR_2_DEF);
        }

        settings.saveSettings();
    }
    /**
     * defines the cell in the combo box to show the image.
     */
    private ListCell<String> playerPictureListCell() {
        return new ListCell<String>() {
            private final ImageView imageView;
            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                imageView = new ImageView();
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    imageView.setImage(playerImgs.get(item));
                    setGraphic(imageView);
                }
            }
        };
    }
    /**
     * sets the picture in the combo box.
     */
    private void setPictureComboBox(ComboBox<String> comboBox) {
        comboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return playerPictureListCell();
            }
        });

        comboBox.setButtonCell(playerPictureListCell());
    }
    /**
     * mange the color or image option.
     */
    @FXML
    private void firstPlayerNoColor() {
        colorPlayerOne.setDisable(true);
        playerOnePicture.setDisable(false);
    }
    /**
     * mange the color or image option.
     */
    @FXML
    private void firstPlayerNoImage() {
        colorPlayerOne.setDisable(false);
        playerOnePicture.setDisable(true);
    }
    /**
     * mange the color or image option.
     */
    @FXML
    private void secondPlayerNoColor() {
        colorPlayerTwo.setDisable(true);
        playerTwoPicture.setDisable(false);
    }
    /**
     * mange the color or image option.
     */
    @FXML
    private void secondPlayerNoImage() {
        colorPlayerTwo.setDisable(false);
        playerTwoPicture.setDisable(true);
    }
}