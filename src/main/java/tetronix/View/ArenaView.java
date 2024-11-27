package tetronix.View;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Model.Arena;

public class ArenaView<T extends Arena> implements ElementViewer<T> {

    @Override
    public void draw(T arena, ScreenManager screenManager) {
        TextGraphics graphics = screenManager.getTextGraphics(); // Obtenção do TextGraphics do GUI
        int rows = arena.getRows();
        int columns = arena.getColumns();
        String[][] background = arena.getBackground();
        int gridCellsize = rows / columns;

        // Desenhar o fundo da arena
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (background[r][c] == null) {
                    graphics.setBackgroundColor(TextColor.Factory.fromString("white"));
                    graphics.fillRectangle(new TerminalPosition(c, r), new TerminalSize(gridCellsize, gridCellsize), ' ');
                } else {
                    graphics.setBackgroundColor(TextColor.Factory.fromString(background[r][c]));
                    graphics.fillRectangle(new TerminalPosition(c, r), new TerminalSize(gridCellsize, gridCellsize), ' ');
                }
            }
        }

        //Prototype
        graphics.setBackgroundColor(TextColor.Factory.fromString("white")); // Fundo branco
        graphics.setForegroundColor(TextColor.Factory.fromString("black")); // Texto preto

        graphics.putString(25, 4, "Level: " + 2);
        graphics.putString(25, 6, "Score: " + 20);
    }
}
