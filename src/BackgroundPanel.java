import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BackgroundPanel extends JPanel implements ActionListener {
    private Image backgroundImage;
    private final List<SnowFlake> snowFlakes;
    private final Random random;
    private final Timer timer;

    public BackgroundPanel(Image background) {
        this.backgroundImage = background;
        this.snowFlakes = new ArrayList<>();
        this.random = new Random();
        
        // Generate initial snowflakes
        for (int i = 0; i < 100; i++) {
            snowFlakes.add(new SnowFlake(random.nextInt(800), random.nextInt(600)));
        }

        // Timer for animation (approx 60 FPS)
        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(10, 20, 40)); // Dark night blue fallback
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw snow
        g.setColor(Color.WHITE);
        for (SnowFlake flake : snowFlakes) {
            g.fillOval(flake.x, flake.y, flake.size, flake.size);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (SnowFlake flake : snowFlakes) {
            flake.y += flake.speed;
            flake.x += Math.sin(flake.y * 0.05) * 1; // Gentle swaying

            // Reset if goes off screen
            if (flake.y > getHeight()) {
                flake.y = -10;
                flake.x = random.nextInt(getWidth());
            }
        }
        repaint();
    }

    private class SnowFlake {
        int x, y;
        int size;
        int speed;

        public SnowFlake(int x, int y) {
            this.x = x;
            this.y = y;
            this.size = random.nextInt(5) + 2;
            this.speed = random.nextInt(3) + 1;
        }
    }
}
