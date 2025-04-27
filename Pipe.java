

import java.awt.*;
import javax.swing.*;

public class Pipe {
    private int x, y;
    private int width, height;
    private boolean isTop;
    private boolean passed;
    private static final int SPEED = 3;
    private Image pipeImage;
    private boolean useImage = false;

    public Pipe(int x, int y, int height, boolean isTop) {
        this.x = x;
        this.y = y;
        this.width = GamePanel.PIPE_WIDTH;
        this.height = height;
        this.isTop = isTop;
        this.passed = false;
        loadImage();
    }
    
    private void loadImage() {
        try {
            String imagePath = isTop ? "toppipe.png" : "bottompipe.png";
            pipeImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            useImage = true;
        } catch (Exception e) {
            useImage = false;
            System.out.println("Pipe image not found, using default graphics");
        }
    }
    
    public void update() {
        x -= SPEED;
    }
    
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        if (useImage && pipeImage != null) {
            g2d.drawImage(pipeImage, x, y, width, height, null);
        } else {
            // Fallback to default graphics if image not available
            g2d.setColor(Color.GREEN);
            g2d.fillRect(x, y, width, height);
            
            // Draw pipe border
            g2d.setColor(new Color(0, 100, 0));
            g2d.drawRect(x, y, width, height);
            
            // Draw pipe cap
            int capWidth = width + 10;
            int capHeight = 20;
            int capX = x - 5;
            int capY = isTop ? y + height - capHeight : y;
            g2d.fillRect(capX, capY, capWidth, capHeight);
        }
    }
    
    public boolean intersects(Bird bird) {
        return bird.getBounds().intersects(new Rectangle(x, y, width, height));
    }
    
    public int getX() {
        return x;
    }
    
    public boolean isPassed() {
        return passed;
    }
    
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}