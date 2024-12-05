package tetronix.View;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Menu;

import java.io.IOException;


public class MenuView implements ElementViewer<Menu> {
    private Menu menu;
    private ScreenManager screenManager;

    public MenuView(Menu menu) {
        this.screenManager = menu.getScreenManager();
    }

    @Override
    public void draw() {

        TextGraphics graphics = screenManager.getTextGraphics();
        graphics.putString(10, 5, "TETRONIX", SGR.BOLD);
        graphics.putString(10, 7, "Press ENTER to Start");
        graphics.putString(10, 9, "Press ESC to Exit");
    }
}
