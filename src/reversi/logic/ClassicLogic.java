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
import java.util.Iterator;

import static reversi.player.PlayerKind.*;

public class ClassicLogic extends Logic {
    /**
     * Constructor.
     */
    public ClassicLogic() { }
    /**
     * @return the color of the starting player.
     */
    public PlayerKind startingPlayer() {
        return First;
    }
    /**
     * @param board the board of the game.
     * @return the winner based on the board state.
     */
    public PlayerKind getWinner(Board board) {
        int blackNum = board.countColor(First);
        int whiteNum = board.countColor(Second);

        if (blackNum > whiteNum) {
            return First;
        } else if (whiteNum > blackNum) {
            return Second;
        } else {
            return None;
        }
    }
    /**
     * @param newGame true if it is the first turn in the game, false otherwise.
     * @param board   the board of the game.
     * @return the color of the next player.
     */
    public PlayerKind nextTurn(boolean newGame, Board board) {
        if (gameOver(board)) {
            setCurrentTurn(PlayerKind.None);
        }

        if (newGame) {
            setCurrentTurn(First);
            return getCurrentTurn();
        }

        setCurrentTurn(PlayerKind.otherColor(getCurrentTurn()));
        return getCurrentTurn();
    }
    /**
     * Checks the possible moves of a player.
     *
     * @param player the color of the player whom we check his possible moves.
     * @param board  the board of the game.
     * @return the possible moves of a player.
     */
    public ArrayList<Position> possibleMoves(PlayerKind player, Board board) {
        ArrayList<Position> possiblePositions = new ArrayList<>();
        for (int i = 1; i <= board.getSize(); ++i) {
            for (int j = 1; j <= board.getSize(); ++j) {
                if (board.getColorAtPosition(i, j) == None && !checkAllDirections(PlayerKind.otherColor(player), i, j,
                                                                                  board).isEmpty()) {
                    possiblePositions.add(new Position(i, j));
                }
            }
        }
        return possiblePositions;
    }
    /**
     * Checks if the game ended.
     *
     * @param board the board of the game.
     * @return true if the game ended, false otherwise.
     */
    public boolean gameOver(Board board) {
        ArrayList<Position> blackMoves = possibleMoves(First, board);
        ArrayList<Position> whiteMoves = possibleMoves(Second, board);

        return blackMoves.isEmpty() && whiteMoves.isEmpty();
    }
    /**
     * Place a token of a player and flips the rival tokens.
     *
     * @param color the color of the player.
     * @param row   the row to place the token.
     * @param col   the column to place the token.
     * @param board the board of the game.
     */
    public void placeAToken(PlayerKind color, int row, int col, Board board) {
        ArrayList<ArrayList<Integer>> directions = checkAllDirections(PlayerKind.otherColor(color), row, col, board);

        ArrayList<Integer> direction;
        for (Iterator<ArrayList<Integer>> it = directions.iterator(); it.hasNext(); ) {
            direction = it.next();
            int rowChange = direction.get(0), colChange = direction.get(1);

            for (int nextRow = row, nextColumn = col; ; nextRow += rowChange, nextColumn += colChange) {
                PlayerKind nextColor = board.getColorAtPosition(nextRow, nextColumn);

                if (nextColor == color && (nextRow != row || nextColumn != col)) {
                    break;
                }

                board.setColorAtPosition(nextRow, nextColumn, color);
            }
        }
    }
    /**
     * Checks if there will be a flip in all the directions of a position.
     *
     * @param playerKind the rival color.
     * @param row        the row of the position.
     * @param col        the column of the position,
     * @param board      the board of the game.
     * @return the direction which should be flipped in case a token is placed at the given position.
     */
    private ArrayList<ArrayList<Integer>> checkAllDirections(PlayerKind playerKind, int row, int col, Board board) {
        ArrayList<ArrayList<Integer>> possibleDirections = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (checkOneDirection(playerKind, row, col, i, j, board)) {
                        ArrayList<Integer> direction = new ArrayList<Integer>();
                        direction.add(i);
                        direction.add(j);

                        possibleDirections.add(direction);
                    }
                }
            }
        }

        return possibleDirections;
    }
    /**
     * Checks if there will be a flip in one direction of a position.
     *
     * @param playerKind the rival color.
     * @param row        the row of the position.
     * @param col        the column of the position,
     * @param rowChange  the row change of every step in order to know the direction.
     * @param colChange  the column change of every step in order to know the direction.
     * @param board      the board of the game.
     * @return true if there will be a flip, false otherwise.
     */
    private boolean checkOneDirection(PlayerKind playerKind, int row, int col, int rowChange, int colChange,
                                      Board board) {
        int firstRowCheck = row + rowChange, firstColCheck = col + colChange;

        for (int rowCheck = firstRowCheck, nextColumn = firstColCheck; ; rowCheck += rowChange, nextColumn +=
                colChange) {

            if (nextColumn <= 0 || nextColumn > board.getSize() || rowCheck <= 0 || rowCheck > board.getSize()) {
                return false;
            }

            PlayerKind nextColor = board.getColorAtPosition(rowCheck, nextColumn);

            if (nextColor == None) {
                return false;
            }

            if (nextColor == PlayerKind.otherColor(playerKind)) {
                // is it the nearest position to the check cell?
                return rowCheck != firstRowCheck || nextColumn != firstColCheck;
            }
        }
    }
}




