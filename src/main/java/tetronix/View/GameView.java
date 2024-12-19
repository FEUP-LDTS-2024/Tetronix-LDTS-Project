package tetronix.View;


import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;

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
    private NextBlockView nextBlockView;

    private List<ElementViewer> elements = new ArrayList<>();


    public GameView(Game game) {
        this.arenaView = new ArenaView(game,this);
        this.tetrisBlockView = new TetrisBlockView(game,this);
        this.coinView = new CoinView(game,this);
        this.bombView = new BombView(game,this);
        this.scoreView = new ScoreView(game,this);
        this.nextBlockView = new NextBlockView(game,this);
        this.screenManager = game.getScreenManager();
    }


    public void addElementToList(ElementViewer elementViewer){
        elements.add(elementViewer);
    }

    public void render() throws IOException {
        screenManager.clear();

        for(ElementViewer element : elements){
            element.draw();
        }

        screenManager.refresh();
    }

}