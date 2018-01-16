package reversi.gui.settings;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/

import javafx.scene.paint.Color;
import reversi.player.PlayerKind;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Settings {
    public static final String BOARD_SIZE_DEF = "BOARD_SIZE";
    public static final String STARTING_PLAYER_DEF = "STARTING_PLAYER";
    public static final String COLOR_1_DEF = "COLOR_1_COLOR";
    public static final String IMAGE_1_DEF = "COLOR_1_IMAGE";
    public static final String COLOR_2_DEF = "COLOR_2_COLOR";
    public static final String IMAGE_2_DEF = "COLOR_2_IMAGE";
    private static final String DEFAULT_SETTINGS_FILE = "config.ini";
    private static final ArrayList<String> SETTINGS_DEFINITION = new ArrayList<>(
            Arrays.asList(BOARD_SIZE_DEF, STARTING_PLAYER_DEF, COLOR_1_DEF, COLOR_2_DEF, IMAGE_1_DEF, IMAGE_2_DEF));
    private static final String DEFAULT_BOARD_SIZE = "8";
    private static final String DEFAULT_STARTING_PLAYER = "1";
    private static final String DEFAULT_COLOR_1 = "#000000";
    private static final String DEFAULT_COLOR_2 = "#ffffff";
    private Map<String, String> configs;
    private String fileName;
    private boolean color_error = false;
    /**
     * A 'singlton designed' constructor.
     * Reads the setting from the configuration file.
     *
     * @return the settings.
     */
    public Settings() {
        this.fileName = DEFAULT_SETTINGS_FILE;
        this.configs = new TreeMap<>();

        // define default settings
        configs.put(BOARD_SIZE_DEF, DEFAULT_BOARD_SIZE);
        configs.put(STARTING_PLAYER_DEF, DEFAULT_STARTING_PLAYER);

        try {
            loadSettings();
        } catch (Exception e) {
        }
        // takes care of lacking date in the file - sets default values.
        if (!configs.containsKey(COLOR_1_DEF) && !configs.containsKey(IMAGE_1_DEF)) {
            configs.put(COLOR_1_DEF, DEFAULT_COLOR_1);
        }

        if (!configs.containsKey(COLOR_2_DEF) && !configs.containsKey(IMAGE_2_DEF)) {
            configs.put(COLOR_2_DEF, DEFAULT_COLOR_2);
        }
    }
    /**
     * converts the settings written in the file to the wanted options and saves them.
     */
    public void loadSettings() throws Exception {
        String line;

        if (!new File(fileName).exists()) {
            throw new Exception("Configuration file does not exist.");
        }

        BufferedReader configFile = new BufferedReader(new FileReader(fileName));

        line = configFile.readLine();
        while (line != null) {
            parseLine(line);
            line = configFile.readLine();
        }

        configFile.close();
    }
    /**
     * read and converts date from a single line in the file.
     *
     * @param line in the file.
     * @return
     */
    public void parseLine(String line) {
        if (line.contains(":")) {
            int cutPos = line.indexOf(':');
            String def = line.substring(0, cutPos);
            String content = line.substring(cutPos + 1);
            if (SETTINGS_DEFINITION.contains(def)) {
                configs.put(def, content);
            }
        }
    }
    /**
     * @return the board size written in the file.
     */
    public int getBoardSize() {
        return Integer.parseInt(configs.get(BOARD_SIZE_DEF));
    }
    /**
     * @return the starting player written in the file.
     */
    public int getStartingPlayer() {
        return Integer.parseInt(configs.get(STARTING_PLAYER_DEF));
    }
    /**
     * @return the first player color written in the file.
     */
    public Color getFirstColor() { return getColor(configs.get(COLOR_1_DEF));}
    /**
     * @return the image path of the first image.
     */
    public String getFirstPicture() { return configs.get(IMAGE_1_DEF);}
    /**
     * @return the second player color written in the file.
     */
    public Color getSecondColor() { return getColor(configs.get(COLOR_2_DEF));}
    /**
     * @return the image path of the second image.
     */
    public String getSecondPicture() { return configs.get(IMAGE_2_DEF);}
    /**
     * @return the kind of player written in a string.
     */
    private PlayerKind checkBlackOrWhite(String color) {
        if (configs.get(STARTING_PLAYER_DEF) == "BLACK") {
            return PlayerKind.First;
        }

        if (configs.get(STARTING_PLAYER_DEF) == "WHITE") {
            return PlayerKind.Second;
        }

        return PlayerKind.None;
    }
    /**
     * @return the kind of color written in a string.
     */
    private Color getColor(String color1) {
        Color c;
        try {
            c = Color.web(color1);
        } catch (Exception e) {
            System.err.println("Invalid color" + e.getMessage());

            if (!color_error) {
                c = Color.BLACK;
                color_error = true;
            } else {
                c = Color.WHITE;
            }
        }
        return c;
    }
    /**
     * @return if the config list contains a string as 'def' .
     */
    public boolean containDefinition(String definition) {
        return configs.containsKey(definition);
    }
    public void setDefinition(String definition, String value) {
        if (SETTINGS_DEFINITION.contains(definition))
            configs.put(definition, value);
    }
    /**
     * if the config list contains a string as 'def' , delete it.
     *
     * @param definition the wanted definition to be removed.
     */
    public void removeDefinition(String definition) {
        if (configs.containsKey(definition))
            configs.remove(definition);
    }
    /**
     * update the settings file, save the current settings.
     */
    public void saveSettings() {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (String setting_definition : SETTINGS_DEFINITION) {
                if (containDefinition(setting_definition))
                    writer.write(setting_definition + ":" + configs.get(setting_definition) + System.lineSeparator());
            }
            writer.close();
        } catch (Exception e) {
            return;
        }
    }
}