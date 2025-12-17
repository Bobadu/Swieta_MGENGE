import java.util.concurrent.ThreadLocalRandom;

public class Board {
    private final int SIZE = 10;
    private final Field[][] grid;
    private int targetsLeft;

    public Board(int targetCount) {
        this.grid = new Field[SIZE][SIZE];
        this.targetsLeft = targetCount;
        initializeBoard();
        placeTargets(targetCount);
    }

    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Field();
            }
        }
    }

    private void placeTargets(int count) {
        int placed = 0;
        while (placed < count) {
            int x = ThreadLocalRandom.current().nextInt(SIZE);
            int y = ThreadLocalRandom.current().nextInt(SIZE);

            // Jeśli pole jest puste, wstawiamy tam punkty (1-10)
            if (!grid[x][y].hasTreasure()) {
                // Generuje punkty od 1 do 10
                int points = ThreadLocalRandom.current().nextInt(10) + 1;
                grid[x][y].setTreasure(points);
                placed++;
            }
        }
    }

    // Metoda obsługująca strzał
    public int shoot(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return -1; // Kod błędu
        }

        Field target = grid[x][y];

        if (target.isRevealed()) {
            return 0;
        }

        int points = target.reveal();
        if (points > 0) {
            targetsLeft--;
        }
        return points;
    }

    public boolean areTargetsLeft() {
        return targetsLeft > 0;
    }

    public int getSize() {
        return SIZE;
    }
}
