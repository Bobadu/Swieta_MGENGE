public class Player {
    private final String name;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public void addPoints(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}
