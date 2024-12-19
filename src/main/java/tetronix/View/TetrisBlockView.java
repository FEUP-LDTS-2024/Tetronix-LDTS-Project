package tetronix.View;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;

public class TetrisBlockView implements ElementViewer<TetrisBlock> {

    private Game game;
    private final ScreenManager screenManager;
    private TetrisBlock block;

    public TetrisBlockView(Game game_ , GameView gameView){
        this.game = game_;
        this.screenManager = game_.getScreenManager();
        gameView.addElementToList(this);
    }


    @Override
    public void draw(){
        block = game.getTetris_block();
        if(block == null) return;


        TextGraphics graphics = screenManager.getTextGraphics();
        int[][] shape = block.getShape();
        Position position = block.getPosition();
        String color = block.getColor();

        graphics.setBackgroundColor(TextColor.Factory.fromString(color));

        // Renderiza cada célula do bloco
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] == 1) {
                    int x = position.getColumn_identifier() + c * 2;
                    int y = position.getRow_identifier() + r;
                    graphics.fillRectangle(new TerminalPosition(x, y), new TerminalSize(2, 1), ' ');
                }
            }
        }
    }

}