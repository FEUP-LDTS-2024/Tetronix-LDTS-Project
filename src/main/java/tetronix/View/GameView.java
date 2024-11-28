package tetronix.View;


import tetronix.Game;
import tetronix.Model.Arena;
import tetronix.Model.TetrisBlock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameView {
    private final ArenaView arenaView;
    private final TetrisBlockView tetrisBlockView;
    private final ScreenManager screenManager;
    private List<ElementViewer> elements = new ArrayList<>();

    public GameView(Game game_) {
        this.arenaView = new ArenaView(game_);        // View da arena
        this.tetrisBlockView = new TetrisBlockView(game_); // View do bloco ativo
        this.screenManager = game_.getScreenManager();

        elements.add(arenaView);
        elements.add(tetrisBlockView);
    }


    public void render() throws IOException {
        screenManager.clear();

        for(ElementViewer element : elements){
            element.draw();
        }

        screenManager.refresh();
    }
}
