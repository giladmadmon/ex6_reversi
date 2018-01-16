package reversi.player;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/


/**
 * Player kind.
 * players color enum
 */
public enum PlayerKind {
    First('X'), Second('O'), None(' ');
    private final char asChar;

    /**
     * create enum
     * @param asChar the char of the enum
     */
    private PlayerKind(char asChar) {
        this.asChar = asChar;
    }

    /**
     * other color gets the other color of the playerKind
     * "X" returns "O", "O" returns "X", " " returns " "
     * @param playerKind the color to get the opposite from
     * @return the other color of playerKind
     */
    public static PlayerKind otherColor(PlayerKind playerKind) {
        if (playerKind == None)
            return None;

        if (playerKind == First) {
            return Second;
        }
        return First;
    }
}
