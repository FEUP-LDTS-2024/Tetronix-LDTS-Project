package tetronix.View;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;

public class NextBlockView implements ElementViewer<TetrisBlock> {
    private Game game;
    private ScreenManager screenManager;
    private TetrisBlock nextBlock;
    private Position previewPosition;

    public NextBlockView(Game game_, GameView gameView){
        this.game=game_;
        this.screenManager = game.getScreenManager();
        previewPosition = new Position(25, 11); // Ajuste os valores conforme necessário
        gameView.addElementToList(this);
    }

    @Override
    public void draw() {
        this.nextBlock = game.getNextBlock();

        if (nextBlock == null) return;

        TextGraphics graphics = screenManager.getTextGraphics();

        graphics.putString(25, 8, "NEXTBLOCK" );

        graphics.setBackgroundColor(TextColor.Factory.fromString("white"));

        graphics.fillRectangle(new TerminalPosition(24, 10), new TerminalSize(10, 6), ' ');

        drawAtPosition();
    }



    private void drawAtPosition() {
        if (nextBlock == null) return;

        TextGraphics graphics = screenManager.getTextGraphics();
        int[][] shape = nextBlock.getShape();
        String color = nextBlock.getColor();

        graphics.setBackgroundColor(TextColor.Factory.fromString(color));

        // Renderiza o bloco usando a posição fornecida
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] == 1) {
                    int x = previewPosition.getColumn_identifier() + c * 2;
                    int y = previewPosition.getRow_identifier() + r;
                    graphics.fillRectangle(new TerminalPosition(x, y), new TerminalSize(2, 1), ' ');
                }
            }
        }
    }
}
