package reversi.gui.game_board;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/
public interface MoveListener {
    /**
     * the action to do when a move has been made.
     *
     * @param row the row of the move.
     * @param col the column of the move.
     */
    void moveListen(int row,int col);
}
