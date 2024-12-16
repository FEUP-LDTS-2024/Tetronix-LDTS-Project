package tetronix.View;


import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameView {
    private final ArenaView arenaView;
    private final TetrisBlockView tetrisBlockView;
    private CoinView coinView;
    private final ScreenManager screenManager;
    private BombView bombView;
    private ScoreView scoreView;
    private List<ElementViewer> elements = new ArrayList<>();


    //ficar por causa do scor, secalhar mais tarde sai
    private Game game;

    public GameView(Game game) {
        this.arenaView = new ArenaView(game);        // View da arena
        this.tetrisBlockView = new TetrisBlockView(game); // View do bloco ativo
        this.coinView = new CoinView(game);
        this.bombView = new BombView(game);
        this.scoreView = new ScoreView(game);
        this.screenManager = game.getScreenManager();

        //talvez saia mais tarde
        this.game = game;

        elements.add(arenaView);
        elements.add(bombView);
        elements.add(scoreView);
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