package tetronix.View;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;

public class NextBlockView {
    private Game game;
    private ScreenManager screenManager;

    public NextBlockView(Game game_) {
        this.game = game_;
        this.screenManager = game.getScreenManager();

    }

    public void renderNextBlock(TetrisBlock nextBlock) {
        if (nextBlock == null) return;

        TextGraphics graphics = screenManager.getTextGraphics();

        graphics.putString(25, 8, "NEXTBLOCK");

        //onde o próximo bloco será desenhado
        graphics.setBackgroundColor(TextColor.ANSI.WHITE);

        graphics.fillRectangle(new TerminalPosition(24, 10), new TerminalSize(10, 6), ' ');

        Position previewPosition = new Position(25, 11);

        TetrisBlockView blockView = new TetrisBlockView(game);
        blockView.drawAtPosition(nextBlock, previewPosition);
    }
}
