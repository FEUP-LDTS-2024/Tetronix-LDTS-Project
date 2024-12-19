package tetronix.View;


import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Menu;


public class GameOverView implements ElementViewer{
    private Game game;
    private ScreenManager screenManager;
    private Menu menu;
    public GameOverView(ScreenManager screenManager, Game game_){
        this.screenManager=screenManager;
        this.game = game_;
    }

    @Override
    public void draw(){
        TextGraphics graphics = screenManager.getTextGraphics();

        graphics.setBackgroundColor(TextColor.Factory.fromString("black"));
        graphics.fillRectangle(new TerminalPosition(2, 5), new TerminalSize(30, 10), ' ');
        graphics.enableModifiers(SGR.BOLD); // Opcional: torna o texto em negrito
        graphics.putString(5, 6, "========GAME OVER========");
        graphics.setForegroundColor(TextColor.ANSI.RED);
        graphics.putString(3, 10, "YOUR SCORE: "+game.get_Additional_Points());
        graphics.putString(3, 11, "PRESS ANY KEY FOR CONTINUE");

    }
}
