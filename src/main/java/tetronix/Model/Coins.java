package tetronix.Model;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

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
