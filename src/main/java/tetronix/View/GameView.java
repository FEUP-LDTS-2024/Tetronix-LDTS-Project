package tetronix.View;


import tetronix.Model.Arena;
import tetronix.Model.TetrisBlock;

import java.io.IOException;

public class GameView {
    private final ArenaView arenaView;
    private final TetrisBlockView tetrisBlockView;

    public GameView() {
        this.arenaView = new ArenaView();        // View da arena
        this.tetrisBlockView = new TetrisBlockView(); // View do bloco ativo
    }


    public void render(Arena arena, TetrisBlock block, ScreenManager screenManager) throws IOException {
        screenManager.clear();

        arenaView.draw(arena, screenManager);
        tetrisBlockView.draw(block,screenManager);

        screenManager.refresh();
    }
}
