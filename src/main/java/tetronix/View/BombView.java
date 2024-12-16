package tetronix.View;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Bomb;
import tetronix.Model.Position;

import java.util.ArrayList;
import java.util.List;


public class BombView implements ElementViewer<Bomb>{
    private Game game;
    private TextGraphics graphics;
    private List<Bomb> bombs = new ArrayList<>();

    public BombView(Game game){
        this.bombs=game.getBomb();
        this.game=game;
        this.graphics= game.getScreenManager().getTextGraphics();
    }

    @Override
    public void draw() {
        graphics.setForegroundColor(TextColor.ANSI.RED);
        for (Bomb bomb : bombs) {
            if (!bomb.isExpired()) {
                Position position = bomb.getPosition();
                graphics.putString(
                        position.getColumn_identifier(),
                        position.getRow_identifier(),
                        "B"
                );
            }
        }
    }


}