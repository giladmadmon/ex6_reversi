package reversi.gui.game_board;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import reversi.board.Board;
import reversi.board.Position;
import reversi.gui.PaneManager;
import reversi.gui.settings.Settings;
import reversi.logic.ClassicLogic;
import reversi.logic.Logic;
import reversi.player.Player;
import reversi.player.PlayerKind;
import reversi.printer.GuiPrinter;
import reversi.printer.Printer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable, MoveListener {
    private static final double WIDTH_PADDING = 25;
    private static final double HEIGHT_PADDING = 0;
    private static final double SIDE_PANEL_WIDTH = 120;
    private Logic logic;
    private List<Position> possibleMoves;
    private Board board;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player noPlayer;
    private Printer printer;
    @FXML
    private GridPane root;
    @FXML
    private Label firstPlayerScore;
    @FXML
    private Label secondPlayerScore;
    @FXML
    private BorderPane winnerPane;
    @FXML
    private BorderPane firstPlayerColorPane;
    @FXML
    private BorderPane secondPlayerColorPane;
    @FXML
    private BorderPane currentPlayerColorPane;
    /**
     * Initialize the menu.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Settings settings = new Settings();
        logic = new ClassicLogic();
        printer = new GuiPrinter();

        if (settings.containDefinition(Settings.COLOR_1_DEF))
            firstPlayer = new Player(settings.getFirstColor());
        else
            firstPlayer = new Player(settings.getFirstPicture());

        if (settings.containDefinition(Settings.COLOR_2_DEF))
            secondPlayer = new Player(settings.getSecondColor());
        else
            secondPlayer = new Player(settings.getSecondPicture());

        noPlayer = new Player(Color.TRANSPARENT);
        board = new Board(settings.getBoardSize(), firstPlayer, secondPlayer, noPlayer);
        board.setPrefHeight(root.getPrefHeight() - HEIGHT_PADDING);
        board.setPrefWidth(root.getPrefWidth() - WIDTH_PADDING - SIDE_PANEL_WIDTH);
        board.addMoveListener(this);
        root.add(board, 0, 0, 1, 2);
        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                board.setPrefWidth(newValue.doubleValue() - WIDTH_PADDING - SIDE_PANEL_WIDTH);
                board.draw();
            }
        });

        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                board.setPrefHeight(newValue.doubleValue() - HEIGHT_PADDING);
                board.draw();
            }
        });

        firstPlayerColorPane.setCenter(
                firstPlayer.getNode(firstPlayerColorPane.getPrefWidth(), firstPlayerColorPane.getPrefHeight()));

        secondPlayerColorPane.setCenter(
                secondPlayer.getNode(secondPlayerColorPane.getPrefWidth(), secondPlayerColorPane.getPrefHeight()));

        nextTurn(true);
    }
    /**
     * back to menu button from game scene.
     * replace the showing scene to the menu.
     */
    @FXML
    private void backToMenu() {
        try {
            PaneManager paneManager = PaneManager.getInstance();
            Scene scene = new Scene(paneManager.getMainMenu(), root.getScene().getWidth(), root.getScene().getHeight());
            ((Stage) firstPlayerScore.getScene().getWindow()).setScene(scene);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * move listener. deal with board cell getting clicked at.
     * if the click is done in a valid position, he execute the move.
     *
     * @param row clicked row.
     * @param col clicked column.
     */
    @Override
    public void moveListen(int row, int col) {
        boolean valid = false;
        for (Position position : possibleMoves) {
            if (position.getRow() == row && position.getColumn() == col) {
                valid = true;
            }
        }
        if (valid) {
            logic.placeAToken(logic.currentTurn(), row, col, board);
            nextTurn(false);
            if (logic.gameOver(board)) {
                printer.printWinner(logic.getWinner(board));
            } else {
                if (possibleMoves.isEmpty()) {
                    printer.printNoMoves(logic.currentTurn());
                    nextTurn(false);
                }
            }
        }
    }
    /**
     * next turn switches turns between the players.
     * update score, current turn, current winner, update possible moves.
     */
    private void nextTurn(boolean newGame) {
        logic.nextTurn(newGame, board);

        // update score
        firstPlayerScore.setText(Integer.toString(board.countColor(PlayerKind.First)));
        secondPlayerScore.setText(Integer.toString(board.countColor(PlayerKind.Second)));

        // change winner color
        winnerPane.setCenter(
                getPlayerNode(logic.getWinner(board), winnerPane.getPrefWidth(), winnerPane.getPrefHeight()));

        // change current turn color
        currentPlayerColorPane.setCenter(getPlayerNode(logic.currentTurn(), currentPlayerColorPane.getPrefWidth(),
                                                       currentPlayerColorPane.getPrefHeight()));

        // update possible moves
        possibleMoves = logic.possibleMoves(logic.currentTurn(), board);
        board.setPossibleMoves(possibleMoves);
        board.draw();
    }
    /**
     * 'Node factory'
     *
     * @param playerKind the player who node we want.
     * @param width      the node width.
     * @param height     the node height.
     * @returns the player node in the wanted size.
     */
    private Node getPlayerNode(PlayerKind playerKind, double width, double height) {
        Node node = null;
        switch (playerKind) {
            case First:
                node = firstPlayer.getNode(width, height);
                break;
            case Second:
                node = secondPlayer.getNode(width, height);
                break;
            case None:
                node = noPlayer.getNode(width, height);
                break;
        }
        return node;
    }
}