package tetronix.View;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Bomb;
import tetronix.Model.Position;

import java.util.ArrayList;
import java.util.List;


public class BombView implements ElementViewer<Bomb>{
    private TextGraphics graphics;
    private List<Bomb> bombs = new ArrayList<>();

    public BombView(Game game, GameView gameView){
        this.bombs=game.getBomb();
        this.graphics= game.getScreenManager().getTextGraphics();
        gameView.addElementToList(this);
    }


    @Override
    public void draw() {
        graphics.setBackgroundColor(TextColor.ANSI.WHITE); // Set background to default arena color
        graphics.setForegroundColor(TextColor.ANSI.RED); // Set bomb foreground color
        for (Bomb bomb : bombs) {
            if (!bomb.isExpired()) {
                Position position = bomb.getPosition();
                graphics.putString(
                        position.getColumn_identifier(),
                        position.getRow_identifier(),
                        "*"
                );
            }
        }
    }
}