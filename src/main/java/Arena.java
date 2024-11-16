import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;


public class Arena {
    private int columns;
    private int rows;

    public Arena(int columns_, int rows_){
        this.columns = columns_;
        this.rows = rows_;
    }


    public void draw(TextGraphics graphics){
        //Meter cor no fundo da tela
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fill(' ');

    }
}
