package tetronix.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Coins;

public class CoinView implements ElementViewer<Coins> {
    private Coins coins;
    private Game game;
    private TextGraphics graphics;

    public CoinView(Game game_){
        this.coins = game_.getCoins();
        this.game = game_;
        this.graphics = game_.getScreenManager().getTextGraphics();
    }

    @Override
    public void draw(){
        System.out.println("Entrei no render da moeda...\n");
        graphics.setForegroundColor(TextColor.ANSI.YELLOW); // Cor amarela para destacar a moeda
        graphics.putString(coins.getPosition().getColumn_identifier(), coins.getPosition().getRow_identifier(), "$");
        //  }
    }
}
