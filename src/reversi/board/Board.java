package reversi.board;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import reversi.gui.game_board.MoveListener;
import reversi.gui.game_board.MoveNotifier;
import reversi.player.Player;
import reversi.player.PlayerKind;

import java.util.ArrayList;
import java.util.List;

import static reversi.player.PlayerKind.*;

public class Board extends GridPane implements MoveNotifier {
    private int cellHeight, cellWidth;
    private int size;
    private PlayerKind board[][];
    private Player firstPlayer;
    private Player secondPlayer;
    private Player noPlayer;
    private List<MoveListener> moveListeners;
    private List<Position> possibleMoves;
    /**
     * @param size         board size
     * @param firstPlayer
     * @param secondPlayer
     */
    public Board(int size, Player firstPlayer, Player secondPlayer, Player noPlayer) {
        MoveNotifier moveNotifier = this;

        this.size = size;
        this.cellHeight = -1;
        this.cellWidth = -1;
        this.board = new PlayerKind[size][size];
        moveListeners = new ArrayList<>();
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.noPlayer = noPlayer;

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int) event.getX(), y = (int) event.getY();
                int col = x / cellWidth + 1, row = y / cellHeight + 1;
                moveNotifier.moveNotify(row, col);
            }
        });

        reset();
    }
    /**
     * returns the player node needed to put at a specific position
     *
     * @param row the row of the node
     * @param col the column of the node
     * @return the node needed to put at a specific position, null if it is not needed.
     */
    private Node getPlayerNode(int row, int col) {
        Node node = null;
        switch (this.getColorAtPosition(row, col)) {
            case First:
                node = firstPlayer.getNode(cellWidth, cellHeight);
                break;
            case Second:
                node = secondPlayer.getNode(cellWidth, cellHeight);
                break;
            case None:
                for (Position position : possibleMoves) {
                    if (position.getRow() == row && position.getColumn() == col) {
                        node = noPlayer.getNode(cellWidth, cellHeight);
                        break;
                    }
                }
                break;
        }
        return node;
    }
    /**
     * reset the board o the starting opening set up.
     */
    public void reset() {
        int midSize = size / 2;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = None;
            }
        }

        setColorAtPosition(midSize, midSize + 1, First);
        setColorAtPosition(midSize + 1, midSize, First);
        setColorAtPosition(midSize + 1, midSize + 1, Second);
        setColorAtPosition(midSize, midSize, Second);
    }
    /**
     * set the wanted color in the wanted position in the board.
     *
     * @param row   the row.
     * @param col   the col.
     * @param color the color.
     */
    public void setColorAtPosition(int row, int col, PlayerKind color) {
        this.board[row - 1][col - 1] = color;
    }
    /**
     * @param row the row.
     * @param col the col.
     * @return the color in the wanted position in the board.
     */
    public PlayerKind getColorAtPosition(int row, int col) {
        return board[row - 1][col - 1];
    }
    /**
     * counts the numbers of positions that hold a certain color.
     *
     * @param color the color.
     * @return the number he counted.
     */
    public int countColor(PlayerKind color) {
        int count = 0;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (board[i][j] == color) {
                    count++;
                }
            }
        }
        return count;
    }
    /**
     * get the size of the board.
     *
     * @return the size.
     */
    public int getSize() {
        return size;
    }
    /**
     * draw the board.
     */
    public void draw() {
        this.getChildren().clear();
        this.setGridLinesVisible(false);
        this.setGridLinesVisible(true);

        int boardHeight = (int) this.getPrefHeight();
        int boardWidth = (int) this.getPrefWidth();

        cellHeight = boardHeight / size;
        cellWidth = boardWidth / size;

        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                int row = i + 1, col = j + 1;
                Node node = getPlayerNode(row, col);

                BorderPane borderPane = new BorderPane();
                borderPane.setMinSize(cellWidth, cellHeight);
                if (node != null) {
                    borderPane.setCenter(node);
                }
                this.add(borderPane, j, i);
            }
        }
    }
    @Override
    public void moveNotify(int row, int col) {
        for (MoveListener moveListener : moveListeners) {
            moveListener.moveListen(row, col);
        }
    }
    @Override
    public void addMoveListener(MoveListener moveListener) {
        moveListeners.add(moveListener);
    }
    @Override
    public void removeMoveListener(MoveListener moveListener) {
        moveListeners.remove(moveListener);
    }
    /**
     * @param possibleMoves sets the current possible moves in order to draw them.
     */
    public void setPossibleMoves(List<Position> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}


