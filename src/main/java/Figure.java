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



public class Figure {
    private int columns;
    private int rows;
    private int gridCellsize;
    private int [][] block = {{1,0}, {1,0}, {1,1}};

    public Figure(int columns_, int rows_){
        this.columns = columns_;
        this.rows = rows_;
        gridCellsize = rows_ / columns_;
    }

    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#990000"));

        for(int row = 0; row < block.length; row ++){
            for(int col = 0; col < block[0].length; col++){
                if(block[row][col] == 1 ){
                    graphics.fillRectangle(new TerminalPosition(col * gridCellsize, row * gridCellsize), new TerminalSize(gridCellsize, gridCellsize), ' ');
                }
            }
        }


    }
}
