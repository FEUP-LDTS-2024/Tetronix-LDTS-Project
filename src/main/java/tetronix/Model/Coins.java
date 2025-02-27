package tetronix.Model;


public class Coins extends Element {
    private int value;
    private boolean collected;

    public Coins(Position position_, String color_, int value_) {
        super(position_, color_);
        this.value = value_;
        this.collected = false;
    }

    public int getValue() {
        return value;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        if (!collected) {
            collected = true;
        }
    }
}
