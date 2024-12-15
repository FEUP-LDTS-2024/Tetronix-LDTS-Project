package tetronix.View;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Bomb;
import tetronix.Model.Coins;
import tetronix.Model.Position;

import java.util.ArrayList;
import java.util.List;


public class BombView implements ElementViewer<Bomb>{
    private Game game;
    private TextGraphics graphics;
    private List<Bomb> bombs = new ArrayList<>();

    public BombView(Game game_){
        this.bombs=game_.getBomb();
        this.game=game_;
        this.graphics= game_.getScreenManager().getTextGraphics();
    }

    @Override
    public void draw() {
        graphics.setForegroundColor(TextColor.ANSI.RED); // Configura a cor para desenhar bombas

        for (Bomb bomb : bombs) {
            // Obtem a posição da bomba
            Position position = bomb.getPosition();

            // Desenha a bomba na posição correta usando o símbolo "B"
            graphics.putString(
                    position.getColumn_identifier(),
                    position.getRow_identifier(),
                    "B"
            );
        }
    }

}
