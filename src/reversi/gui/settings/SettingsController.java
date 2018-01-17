package reversi.gui.settings;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import reversi.gui.PaneManager;
import reversi.player.PlayerKind;
import reversi.printer.GuiPrinter;
import reversi.printer.Printer;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class SettingsController implements Initializable {
    private static final String PLAYER_PICTURES_DIRECTORY = "pictures/players/";
    private static final String DEFAULT_FIRST_PLAYER_IMAGE = "img1.png";
    private static final String DEFAULT_SECOND_PLAYER_IMAGE = "img2.png";
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
    private Printer printer;
    /**
     * Initialize the settings.
     *
     * @param location
     * @param resources
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        settings = new Settings();
        printer = new GuiPrinter();

        // load all the player images
        playerImgs = new TreeMap<>();
        for (String playerImg : playerOnePicture.getItems()) {
            playerImgs.put(playerImg, new Image(
                    getClass().getClassLoader().getResource(PLAYER_PICTURES_DIRECTORY + playerImg).toString()));
        }

        // change the board size
        boardSize.getSelectionModel().select(Integer.toString(settings.getBoardSize()));

        // change the starting player
        if (settings.getStartingPlayer() == PlayerKind.First) {
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
            playerOnePicture.setValue(DEFAULT_FIRST_PLAYER_IMAGE);
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
            playerTwoPicture.setValue(DEFAULT_SECOND_PLAYER_IMAGE);
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
        if (updateSettings()) {

            try {
                PaneManager paneManager = PaneManager.getInstance();
                Scene scene = new Scene(paneManager.getMainMenu(), boardSize.getScene().getWidth(),
                                        boardSize.getScene().getHeight());
                ((Stage) boardSize.getScene().getWindow()).setScene(scene);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * update the current settings.
     */
    private boolean updateSettings() {
        if (playerOneColorOpt.selectedProperty().get() && playerTwoColorOpt.selectedProperty().get()) {
            if (colorPlayerOne.getValue().equals(colorPlayerTwo.getValue())) {
                printer.printSameTokens();
                return false;
            }
        }

        if (playerOnePictureOpt.selectedProperty().get() && playerTwoPictureOpt.selectedProperty().get()) {
            if (playerOnePicture.getValue().equals(playerTwoPicture.getValue())) {
                printer.printSameTokens();
                return false;
            }
        }

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
        return true;
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