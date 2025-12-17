import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private final JLabel player1ScoreLabel;
    private final JLabel player2ScoreLabel;
    private final JLabel turnLabel;

    public ScorePanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        setOpaque(false);

        player1ScoreLabel = createFestiveLabel("Gracz 1: 0 pkt");
        add(player1ScoreLabel);

        player2ScoreLabel = createFestiveLabel("Gracz 2: 0 pkt");
        add(player2ScoreLabel);

        turnLabel = createFestiveLabel("Tura: ");
        turnLabel.setForeground(new Color(255, 215, 0));
        add(turnLabel);
    }

    private JLabel createFestiveLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 22));
        label.setForeground(Color.WHITE);
        return label;
    }

    public void updateScores(String p1Name, int p1Score, String p2Name, int p2Score) {
        player1ScoreLabel.setText(p1Name + ": " + p1Score + " pkt");
        player2ScoreLabel.setText(p2Name + ": " + p2Score + " pkt");
    }

    public void updateTurn(String currentPlayerName) {
        turnLabel.setText("Tura: " + currentPlayerName);
    }
}
