package tetronix.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;

public class ScoreView implements ElementViewer<Game>{
   /* private int level;
    private int score;
    private int additional_points;
*/
    private Game game;
    private ScreenManager screenManager;


    public ScoreView(Game game_){
        this.game = game_;
        this.screenManager = game_.getScreenManager();
    }

    public void draw(){
        //Prototype for menu
        TextGraphics graphics = screenManager.getTextGraphics();

        graphics.setForegroundColor(TextColor.Factory.fromString("white")); // Texto preto

        graphics.putString(25, 4, "Level: " + game.getLevel());
        graphics.putString(25, 6, "Score: " + (game.getScore() + game.get_Additional_Points()));
    }
}
