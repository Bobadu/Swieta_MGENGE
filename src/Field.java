public class Field {
    private int value;
    private boolean isRevealed;

    public Field() {
        this.value = 0;
        this.isRevealed = false;
    }

    public void setTreasure(int value) {
        this.value = value;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int reveal() {
        this.isRevealed = true;
        return value;
    }

    public boolean hasTreasure() {
        return value > 0;
    }
}
