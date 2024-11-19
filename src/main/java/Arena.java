import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;


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
        int height = block.getShape().length;
        int widht = block.getShape()[0].length;
        int xPos = block.getPosition().getX();
        int yPos = block.getPosition().getY();
        String color = block.getColor();

        for (int x = 0; x < height ; x++) {
            for (int y = 0; y < widht; y++) {
                if(shape[x][y] == 1){
                    background[xPos + y][yPos + x] = color;
                }
            }
        }
    }

    public void draw(TextGraphics graphics){
        //Meter cor no fundo da tela
        for(int x = 0; x < rows; x++){
            for(int y = 0; y < columns; y++){
                if(background[x][y] == null){
                    graphics.setBackgroundColor(TextColor.Factory.fromString("white"));
                    graphics.fillRectangle(new TerminalPosition(x, y), new TerminalSize(gridCellsize, gridCellsize), ' ');
                } else {
                    graphics.setBackgroundColor(TextColor.Factory.fromString(background[x][y]));
                    graphics.fillRectangle(new TerminalPosition(x, y), new TerminalSize(gridCellsize, gridCellsize), ' ');
                }
            }
        }
    }
}



