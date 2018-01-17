package reversi.printer;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import reversi.player.PlayerKind;

public class GuiPrinter implements Printer {
    @Override
    public void printNoMoves(PlayerKind playerKind) {
        new Alert(Alert.AlertType.NONE, playerKind + " player has no available move.", ButtonType.OK).show();
    }
    @Override
    public void printWinner(PlayerKind playerKind) {
        if (playerKind == PlayerKind.None) {
            new Alert(Alert.AlertType.NONE, "It's a tie!!", ButtonType.OK).show();
        } else {
            new Alert(Alert.AlertType.NONE, "The winner is the " + playerKind + " " + "player!!", ButtonType.OK).show();
        }
    }
    @Override
    public void printSameTokens() {
        new Alert(Alert.AlertType.NONE, "There has to be a difference between the players!", ButtonType.OK).show();
    }
}
