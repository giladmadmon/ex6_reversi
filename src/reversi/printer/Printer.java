package reversi.printer;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/

import reversi.player.PlayerKind;

public interface Printer {
    /**
     * Print that the players can not have the same token.
     */
    void printSameTokens();

    /**
     * Print that there are no moves.
     *
     * @param playerKind the color of the player whom has bo moves.
     */
    void printNoMoves(PlayerKind playerKind);

    /**
     * Announce the winner.
     *
     * @param playerKind the color of the winner.
     */
    void printWinner(PlayerKind playerKind);
}