package tetronix.View;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;

public class TetrisBlockView implements ElementViewer<TetrisBlock> {

    @Override
    public void draw(TetrisBlock block, ScreenManager screenManager) {
        TextGraphics graphics = screenManager.getTextGraphics();
        int[][] shape = block.getShape();
        Position position = block.getPosition();
        String color = block.getColor();

        graphics.setBackgroundColor(TextColor.Factory.fromString(color));

        // Renderiza cada célula do bloco
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] == 1) {
                    int x = position.getColumn_identifier() + c;
                    int y = position.getRow_identifier() + r;
                    graphics.fillRectangle(new TerminalPosition(x, y), new TerminalSize(1, 1), ' ');
                }
            }
        }
    }
}

