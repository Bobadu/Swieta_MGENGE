import javax.swing.SwingUtilities;

public class NumberShooterGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow game = new GameWindow();
            game.setVisible(true);
        });
    }
}