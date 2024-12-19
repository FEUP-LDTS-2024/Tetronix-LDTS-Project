package tetronix.View;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Arena;

public class ArenaView implements ElementViewer<Arena> {


    private Game game;
    private final Arena arena;
    private final ScreenManager screenManager;


    public ArenaView(Game game_, GameView gameView){
        this.game = game_;
        this.arena = game.getArena();
        this.screenManager = game.getScreenManager();
        gameView.addElementToList(this);
    }

    @Override
    public void draw(){//T arena, ScreenManager screenManager) {
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
    }
}
