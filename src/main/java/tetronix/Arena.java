package tetronix;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;


public class Arena {
    private int columns;
    private int rows;
    private String[][] background;
    private int gridCellsize;

    public Arena(int columns_, int rows_){
        this.columns = columns_;
        this.rows = rows_;
        this.gridCellsize = rows_ / columns_;
        background = new String[rows_][columns_];
    }

    public String[][] getBackground(){
        return this.background;
    }

    public void moveBlocktoBackground(TetrisBlock block){
        int [][]shape = block.getShape();
        int number_of_rows = block.getShape().length;
        int number_of_columns = block.getShape()[0].length;
        int column_pos = block.getPosition().getColumn_identifier();
        int row_pos = block.getPosition().getRow_identifier();
        String color = block.getColor();

        for (int r = 0; r < number_of_rows ; r++) {
            for (int c = 0; c < number_of_columns; c++) {
                if(shape[r][c] == 1){
                    background[row_pos + r][column_pos + c] = color;
                }
            }
        }
    }

    public void draw(TextGraphics graphics){
        //Meter cor no fundo da tela
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                if(background[r][c] == null){
                    graphics.setBackgroundColor(TextColor.Factory.fromString("white"));
                    graphics.fillRectangle(new TerminalPosition(c,r ), new TerminalSize(gridCellsize, gridCellsize), ' ');
                } else {
                    graphics.setBackgroundColor(TextColor.Factory.fromString(background[r][c]));
                    graphics.fillRectangle(new TerminalPosition(c, r), new TerminalSize(gridCellsize, gridCellsize), ' ');
                }
            }
        }
    }
}



