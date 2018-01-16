package reversi.player;
/**************.
 * Student name: Gilad Madmon
 * Student name: Dafna Magid
 * Exercise name: Exercise 6
 **************/
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static javafx.scene.layout.GridPane.setHalignment;

public class Player {
    private Color color;
    private Image image;
    public Player(Color color) {
        this.color = color;
        this.image = null;
    }

    /**
     * Player constructor.
     * @param imgPath if the player will have an image' we will save her path.
     */
    public Player(String imgPath) throws NullPointerException {
        this.color = null;
        this.image = new Image(getClass().getClassLoader().getResource("pictures/players/" + imgPath).toString());
    }

    /**
     * getNode creates a node in the wanted size.
     * fills the node with the player color\image.
     * if it's an image - resize's the circular image and returns it.
     * if it's a color - creates a circle node with black lining with color filling.
     * @param width the wanted width.
     * @param height the wanted height.
     * @return the node.
     */
    public Node getNode(double width, double height) {
        int minSize = (int) Math.min(width, height);
        int paddineSize = Math.min(minSize / 5, 4);
        if (color == null) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(minSize - paddineSize * 1.5);
            imageView.setFitHeight(minSize - paddineSize * 1.5);
            return imageView;
        }

        int radius = minSize / 2 - paddineSize;
        Circle circle = new Circle(radius, color);
        circle.setStroke(Color.BLACK);
        setHalignment(circle, HPos.CENTER);
        return circle;
    }
}
