import javax.swing.*;
import java.awt.*;


public class FlappyBird extends JFrame {
    public static final int WIDTH = 360;
    public static final int HEIGHT = 640;
    private GamePanel gamePanel;

    public FlappyBird() {
        setTitle("Flappy Bird");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        gamePanel = new GamePanel();
        add(gamePanel);
    }

   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlappyBird game = new FlappyBird();
            game.setVisible(true);
        });
    }
}

