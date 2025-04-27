import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private static final int BIRD_X = 100;
    private static final int BIRD_SIZE = 40;
    public static final int PIPE_WIDTH = 80;
    private static final int PIPE_GAP = 200;
    public static final int JUMP_SPEED = -10;
    public static final int GRAVITY = 1;
    
    private Bird bird;
    private ArrayList<Pipe> pipes;
    private Timer gameTimer;
    private boolean isGameOver;
    private int score;
    private int groundY;
    private Image backgroundImage;
    private boolean useBackgroundImage = false;

    
    public GamePanel() {
        setPreferredSize(new Dimension(FlappyBird.WIDTH, FlappyBird.HEIGHT));
        setBackground(new Color(135, 206, 235)); // Sky blue
        setFocusable(true);
        addKeyListener(this);
        
        groundY = FlappyBird.HEIGHT - 100;
        loadBackgroundImage();
        initGame();
    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = new ImageIcon(getClass().getResource("flappybirdbg.png")).getImage();
            useBackgroundImage = true;
        } catch (Exception e) {
            useBackgroundImage = false;
            System.out.println("Background image not found, using default background");
        }
    }
    
    private void initGame() {
        bird = new Bird(BIRD_X, FlappyBird.HEIGHT / 2);
        pipes = new ArrayList<>();
        score = 0;
        isGameOver = false;
        
        // Add initial pipes
        addPipe();
        
        gameTimer = new Timer(20, this);
        gameTimer.start();
    }
    
    private void addPipe() {
        int minHeight = 50;
        int maxHeight = groundY - PIPE_GAP - minHeight;
        int height = (int) (Math.random() * (maxHeight - minHeight)) + minHeight;
        
        pipes.add(new Pipe(FlappyBird.WIDTH, 0, height, true));
        pipes.add(new Pipe(FlappyBird.WIDTH, height + PIPE_GAP, groundY - (height + PIPE_GAP), false));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw background
        if (useBackgroundImage && backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            // Draw ground
            g2d.setColor(new Color(150, 75, 0));
            g2d.fillRect(0, groundY, getWidth(), getHeight() - groundY);
        }
       
        
        // Draw pipes
        
        for (Pipe pipe : pipes) {
            pipe.draw(g2d);
        }

        // Draw bird
        bird.draw(g2d);  
        
        // Draw score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Comic Sans", Font.BOLD, 24));
        g.drawString("Score: " + score, 20, 30);
        
        if (isGameOver) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Comic Sans", Font.BOLD, 32));
            
            String gameOver = "Game Over!";
            int stringWidth = g2d.getFontMetrics().stringWidth(gameOver);
            g2d.drawString(gameOver, (getWidth() - stringWidth) / 2, getHeight() / 2);
            g2d.setFont(new Font("Comic Sans", Font.BOLD, 24));
            String restart = "Press SPACE to restart";
            stringWidth = g2d.getFontMetrics().stringWidth(restart);
            g2d.drawString(restart, (getWidth() - stringWidth) / 2, getHeight() / 2 + 50);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            bird.update();
            
            // Update pipes
            Iterator<Pipe> it = pipes.iterator();
            while (it.hasNext()) {
                Pipe pipe = it.next();
                pipe.update();
                
                if (pipe.getX() + PIPE_WIDTH < 0) {
                    it.remove();
                }
                
                if (pipe.intersects(bird)) {
                    gameOver();
                }
            }
            
            // Add new pipes
            if (pipes.isEmpty() || pipes.get(pipes.size() - 1).getX() < FlappyBird.WIDTH - 300) {
                addPipe();
            }
            
            // Check if bird hits ground or ceiling
            if (bird.getY() <= 0 || bird.getY() + BIRD_SIZE >= groundY) {
                gameOver();
            }
            
            // Update score
            if (!pipes.isEmpty() && pipes.get(0).getX() + PIPE_WIDTH < BIRD_X && !pipes.get(0).isPassed()) {
                score++;
                pipes.get(0).setPassed(true);
                pipes.get(1).setPassed(true);
            }
        }
        repaint();
    }
    
    private void gameOver() {
        isGameOver = true;
        gameTimer.stop();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (isGameOver) {
                initGame();
            } else {
                bird.jump();
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
} 

