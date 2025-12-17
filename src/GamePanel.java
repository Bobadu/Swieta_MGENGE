import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private final JButton[][] buttons;
    private final int boardSize;

    public GamePanel(int boardSize, ActionListener listener) {
        this.boardSize = boardSize;
        // Make panel transparent so background image (handled by parent) shows through
        setOpaque(false);
        setLayout(new GridLayout(boardSize, boardSize, 2, 2)); // Small gap between gifts
        buttons = new JButton[boardSize][boardSize];
        initializeButtons(listener);
    }

    private void initializeButtons(ActionListener listener) {
        ImageIcon closedIcon = ResourceManager.getScaledImage("gift_closed.png", 50, 50);

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                JButton button = new JButton() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        // Draw semi-transparent background (Frosty effect)
                        g.setColor(new Color(255, 255, 255, 100));
                        g.fillRect(0, 0, getWidth(), getHeight());
                        super.paintComponent(g);
                    }
                };
                button.setPreferredSize(new Dimension(55, 55));
                
                // Style for transparent button with icon
                button.setIcon(closedIcon);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                
                button.setActionCommand(i + "," + j);
                button.addActionListener(listener);
                buttons[i][j] = button;
                add(button);
            }
        }
    }

    public void updateButton(int row, int col, int points) {
        JButton button = buttons[row][col];
        button.setEnabled(false);
        button.setDisabledIcon(button.getIcon()); // Keep icon visible when disabled
        
        if (points > 0) {
            button.setDisabledIcon(ResourceManager.getScaledImage("gift_open_star.png", 50, 50));
            ResourceManager.playSound("jingle_bell.wav");
        } else {
            button.setDisabledIcon(ResourceManager.getScaledImage("coal.png", 50, 50));
            ResourceManager.playSound("crunch.wav");
        }
    }

    public void disableAllButtons() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j].setEnabled(false);
                // Ensure the icon remains visible even when disabled
                if (buttons[i][j].getDisabledIcon() == null) {
                    buttons[i][j].setDisabledIcon(buttons[i][j].getIcon());
                }
            }
        }
    }
}
