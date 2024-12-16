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
    private List<ElementViewer> elements = new ArrayList<>();


    //ficar por causa do scor, secalhar mais tarde sai
    private Game game;

    public GameView(Game game) {
        this.arenaView = new ArenaView(game);        // View da arena
        this.tetrisBlockView = new TetrisBlockView(game); // View do bloco ativo
        this.coinView = new CoinView(game);
        this.bombView = new BombView(game);
        this.screenManager = game.getScreenManager();

        //talvez saia mais tarde
        this.game = game;

        elements.add(arenaView);
        elements.add(tetrisBlockView);
        elements.add(coinView);
        elements.add(bombView);

    }


    public void render() throws IOException {
        screenManager.clear();

        for(ElementViewer element : elements){
            element.draw();
        }
        renderScore();


        screenManager.refresh();
    }


    public void renderScore(){
        //Prototype for menu
        TextGraphics graphics = screenManager.getTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("white")); // Fundo branco
        graphics.setForegroundColor(TextColor.Factory.fromString("black")); // Texto preto

        graphics.putString(25, 4, "Level: " + game.getLevel());
        graphics.putString(25, 6, "Score: " + (game.getScore() + game.get_Additional_Points()));
    }
}