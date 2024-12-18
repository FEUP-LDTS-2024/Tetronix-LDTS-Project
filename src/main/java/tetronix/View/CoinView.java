package tetronix.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Coins;

import java.util.ArrayList;
import java.util.List;

public class CoinView implements ElementViewer<Coins> {
    private Game game;
    private TextGraphics graphics;
    private List<Coins> coins = new ArrayList<>();

    public CoinView(Game game_){
        this.coins = game_.getCoins();
        this.game = game_;
        this.graphics = game_.getScreenManager().getTextGraphics();
    }

    @Override
    public void draw(){
        graphics.setBackgroundColor(TextColor.ANSI.WHITE); // Define o fundo como a cor padrão (sem borda preta)
        for(Coins coin_ : coins){
            if(!coin_.isCollected()){
                if(coin_.getValue() == 4){
                    graphics.setForegroundColor(TextColor.ANSI.YELLOW); // Cor amarela para destacar a moeda
                } else {
                    graphics.setForegroundColor(TextColor.ANSI.BLUE); // Cor amarela para destacar a moeda

                }
                graphics.putString(coin_.getPosition().getColumn_identifier(), coin_.getPosition().getRow_identifier(), "$");
            }
        }
    }
}
