import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GameWindow extends JFrame implements ActionListener {
    private Board board;
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;

    public GameWindow() {
        super("Number Shooter Game - Christmas Edition 🎄");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 800);
        setLocationRelativeTo(null);
        
        ImageIcon cursorIcon = ResourceManager.getScaledImage("cursor_target.png", 32, 32);
        if (cursorIcon != null) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Point hotSpot = new Point(16, 16);
            Cursor cursor = toolkit.createCustomCursor(cursorIcon.getImage(), hotSpot, "Target");
            setCursor(cursor);
        }

        initializeGame();

        ResourceManager.playSound("ho_ho_ho.wav");
        ResourceManager.loopMusic("bg_music.wav");

        setupUI();
        updateGameDisplay();
    }

    private void initializeGame() {
        board = new Board(20);
        String name1 = JOptionPane.showInputDialog(this, "Podaj imię Gracza 1:", "Gracz 1", JOptionPane.QUESTION_MESSAGE);
        if (name1 == null || name1.trim().isEmpty()) name1 = "Mikołaj";
        p1 = new Player(name1);

        String name2 = JOptionPane.showInputDialog(this, "Podaj imię Gracza 2:", "Gracz 2", JOptionPane.QUESTION_MESSAGE);
        if (name2 == null || name2.trim().isEmpty()) name2 = "Elf";
        p2 = new Player(name2);

        currentPlayer = p1;
    }

    private void setupUI() {
        ImageIcon bgIcon = ResourceManager.getImage("background.png");
        Image bgImage = (bgIcon != null) ? bgIcon.getImage() : null;

        BackgroundPanel backgroundPanel = new BackgroundPanel(bgImage);
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        scorePanel = new ScorePanel();
        add(scorePanel, BorderLayout.NORTH);

        JPanel gridContainer = new JPanel(new GridBagLayout());
        gridContainer.setOpaque(false);
        
        gamePanel = new GamePanel(board.getSize(), this);
        gamePanel.setPreferredSize(new Dimension(600, 600));
        
        gridContainer.add(gamePanel);
        add(gridContainer, BorderLayout.CENTER);
    }

    private void updateGameDisplay() {
        scorePanel.updateScores(p1.getName(), p1.getScore(), p2.getName(), p2.getScore());
        scorePanel.updateTurn(currentPlayer.getName());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String[] parts = command.split(",");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);

        int pointsScored = board.shoot(row, col);

        if (pointsScored != -1) { 
            gamePanel.updateButton(row, col, pointsScored);
            if (pointsScored > 0) {
                currentPlayer.addPoints(pointsScored);
            }
            
            if (!board.areTargetsLeft()) {
                endGame();
            } else {
                switchPlayer();
                updateGameDisplay();
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == p1) ? p2 : p1;
    }

    private void endGame() {
        gamePanel.disableAllButtons();
        ResourceManager.stopMusic();
        ResourceManager.playSound("ho_ho_ho.wav");

        String winnerMessage;
        ImageIcon resultIcon;

        if (p1.getScore() > p2.getScore()) {
            winnerMessage = p1.getName() + " wygrywa! 🎁";
            resultIcon = ResourceManager.getScaledImage("santa_win.png", 100, 100);
        } else if (p2.getScore() > p1.getScore()) {
            winnerMessage = p2.getName() + " wygrywa! 🎁";
            resultIcon = ResourceManager.getScaledImage("santa_win.png", 100, 100);
        } else {
            winnerMessage = "REMIS! 🤝";
            resultIcon = ResourceManager.getScaledImage("santa_win.png", 100, 100);
        }

        JOptionPane.showMessageDialog(this, 
            winnerMessage + "\nWynik: " + p1.getScore() + " - " + p2.getScore(), 
            "Koniec Gry", 
            JOptionPane.INFORMATION_MESSAGE,
            resultIcon);
    }
}
