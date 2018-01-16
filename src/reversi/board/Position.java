package reversi.board;

/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/
public class Position implements Comparable<Position> {
    private int row;
    private int column;
    /**
     * Constructor.
     */
    public Position() {
        this.row = 0;
        this.column = 0;
    }
    /**
     * Constructor.
     *
     * @param row the row of the position. Default is 0.
     * @param col the column of the position. Default is 0.
     */
    public Position(int row, int col) {
        this.row = row;
        this.column = col;
    }
    /**
     * @return the row of the position.
     */
    public int getRow() {
        return this.row;
    }
    /**
     * @return the column of the position.
     */
    public int getColumn() {
        return this.column;
    }
    @Override
    public int compareTo(Position position) {
        if (this.getRow() == position.getRow() && this.getColumn() == position.getColumn())
            return 0;

        if (this.getRow() < position.getRow())
            return -1;

        return 1;
    }
}
