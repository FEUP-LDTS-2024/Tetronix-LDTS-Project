package tetronix.View;


import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;


public class GameOverView implements ElementViewer{
    private Game game;
    private ScreenManager screenManager;
    public GameOverView(ScreenManager screenManager){
        this.screenManager= screenManager;
    }
    @Override
    public void draw(){
        TextGraphics graphics = screenManager.getTextGraphics();

        graphics.setBackgroundColor(TextColor.Factory.fromString("black"));
        graphics.fillRectangle(new TerminalPosition(2, 5), new TerminalSize(30, 10), ' ');
        graphics.enableModifiers(SGR.BOLD); // Opcional: torna o texto em negrito
        graphics.putString(6, 8, "    GAME OVER    ");
        graphics.putString(6, 10, "YOUR SCORE IS ");
        graphics.putString(6, 12, "PRESS ANY KEY FOR CONTINUE");

    }
}
