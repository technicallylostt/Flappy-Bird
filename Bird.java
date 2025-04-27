
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Bird {
    private int x, y;
    private int velocity;
    private static final int SIZE = 40;
    private Image birdImage;
    private boolean useImage = false;
    
    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
        loadImage();
    }
    
    private void loadImage() {
        try {
            birdImage = new ImageIcon(getClass().getResource("flappybird.png")).getImage();
            useImage = true;
        } catch (Exception e) {
            useImage = false;
            System.out.println("Bird image not found, using default graphics");
        }
    }
    
  
   
    public void update() {
            velocity += GamePanel.GRAVITY;
            y += velocity;   
        
    }
    
    public void jump() {
        velocity = GamePanel.JUMP_SPEED;
    }
    
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw shadow
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.fill(new Ellipse2D.Float(x + 5, y + SIZE - 5, SIZE - 10, 10));
        
        if (useImage && birdImage != null) {
            g2d.drawImage(birdImage, x, y, SIZE, SIZE, null);
        } else {
            // Fallback to default graphics if image not available
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(x, y, SIZE, SIZE);
            
            // Draw eye
            g2d.setColor(Color.BLACK);
            g2d.fillOval(x + 25, y + 10, 8, 8);
            
            // Draw beak
            g2d.setColor(Color.ORANGE);
            int[] xPoints = {x + 35, x + 45, x + 35};
            int[] yPoints = {y + 15, y + 20, y + 25};
            g2d.fillPolygon(xPoints, yPoints, 3);
        }
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }
} 
