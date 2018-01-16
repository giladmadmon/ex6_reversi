package reversi.logic;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/
import reversi.board.Board;
import reversi.board.Position;
import reversi.player.PlayerKind;

import java.util.ArrayList;

import static reversi.player.PlayerKind.None;

/**
 * .
 * the logic of the game
 */
public abstract class Logic {
    private PlayerKind currentTurn;
    /**
     * Constructor.
     */
    Logic() {
        currentTurn = None;
    }
    /**
     * @return the color of the player whom is his current turn.
     */
    public PlayerKind currentTurn() {
        return currentTurn;
    }
    /**
     * @return the color of the starting player.
     */
    public abstract PlayerKind startingPlayer();

    /**
     * @param board the board of the game.
     * @return the winner based on the board state.
     */
    public abstract PlayerKind getWinner(Board board);

    /**
     * @param newGame true if it is the first turn in the game, false otherwise.
     * @param board   the board of the game.
     * @return the color of the next player.
     */
    public abstract PlayerKind nextTurn(boolean newGame, Board board);

    /**
     * Checks the possible moves of a player.
     *
     * @param player the color of the player whom we check his possible moves.
     * @param board  the board of the game.
     * @return the possible moves of a player.
     */
    public abstract ArrayList<Position> possibleMoves(PlayerKind player, Board board);

    /**
     * Checks if the game ended.
     *
     * @param board the board of the game.
     * @return true if the game ended, false otherwise.
     */
    public abstract boolean gameOver(Board board);

    /**
     * Place a token of a player and flips the rival tokens.
     *
     * @param color the color of the player.
     * @param row   the row to place the token.
     * @param col   the column to place the token.
     * @param board the board of the game.
     */
    public abstract void placeAToken(PlayerKind color, int row, int col, Board board);
    /**
     * @return the current turn.
     */
    protected PlayerKind getCurrentTurn() {
        return this.currentTurn;
    }
    /**
     * change the current turn.
     *
     * @param turn the new current turn.
     */
    protected void setCurrentTurn(PlayerKind turn) {
        this.currentTurn = turn;
    }
}
