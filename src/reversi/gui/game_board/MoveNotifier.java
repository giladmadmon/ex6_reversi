package reversi.gui.game_board;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/
public interface MoveNotifier {
    /**
     * notify a move has been requested.
     * @param row the row of the move.
     * @param col the column of the move.
     */
    void moveNotify(int row, int col);

    /**
     * adds a MoveListener to the listeners list.
     *
     * @param moveListener the move listener to add.
     */
    void addMoveListener(MoveListener moveListener);

    /**
     * removes MoveListener from the listeners list.
     *
     * @param moveListener the MoveListener to remove.
     */
    void removeMoveListener(MoveListener moveListener);
}
