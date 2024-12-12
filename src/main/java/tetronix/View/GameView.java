package tetronix.View;


import tetronix.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameView {
    private final ArenaView arenaView;
    private final TetrisBlockView tetrisBlockView;
    private CoinView coinView;
    private final ScreenManager screenManager;
    private List<ElementViewer> elements = new ArrayList<>();

    public GameView(Game game_) {
        this.arenaView = new ArenaView(game_);        // View da arena
        this.tetrisBlockView = new TetrisBlockView(game_); // View do bloco ativo
        this.coinView = new CoinView(game_);
        this.screenManager = game_.getScreenManager();

        elements.add(arenaView);
        elements.add(tetrisBlockView);
        elements.add(coinView);
    }


    public void render() throws IOException {
        screenManager.clear();

        for(ElementViewer element : elements){
            element.draw();
        }

        screenManager.refresh();
    }
}
