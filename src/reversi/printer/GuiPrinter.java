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
        showMessage(playerKind + " player has no available move.");
    }
    @Override
    public void printWinner(PlayerKind playerKind) {
        if (playerKind == PlayerKind.None) {
            showMessage("It's a tie!!");
        } else {
            showMessage("The winner is the " + playerKind + " " + "player!!");
        }
    }
    @Override
    public void printSameTokens() {
        showMessage("There has to be a difference between the players!");
    }
    private void showMessage(String msg) {
        new Alert(Alert.AlertType.NONE, msg, ButtonType.OK).show();
    }
}
