package tetronix.View;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Arena;
import tetronix.Model.TetrisBlock;

public class ArenaView<T extends Arena> implements ElementViewer<T> {


    private Game game;
    private final Arena arena;
    private final ScreenManager screenManager;


    public ArenaView(Game game_){
        this.game = game_;
        this.arena = game.getArena();
        this.screenManager = game.getScreenManager();
    }

    @Override
    public void draw() {
        TextGraphics graphics = screenManager.getTextGraphics();
        int rows = arena.getRows();
        int columns = arena.getColumns();
        TetrisBlock[][] background = arena.getBackground();
        int gridCellsize = rows / columns;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (background[r][c] == null) {
                    graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF"));
                } else {
                    graphics.setBackgroundColor(TextColor.Factory.fromString(background[r][c].getColor()));
                }
                graphics.fillRectangle(new TerminalPosition(c, r), new TerminalSize(gridCellsize, gridCellsize), ' ');
            }
        }

        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));

        graphics.putString(25, 4, "Level: " + game.getLevel());
        graphics.putString(25, 6, "Score: " + game.getScore());
    }
}
