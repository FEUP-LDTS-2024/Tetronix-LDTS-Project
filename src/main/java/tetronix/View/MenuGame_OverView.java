package tetronix.View;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Model.Menu;

public class MenuGame_OverView implements ElementViewer<Menu> {
    private final Menu menu;
    private final ScreenManager screenManager;

    public MenuGame_OverView(Menu menu) {
        this.menu = menu;
        this.screenManager = menu.getScreenManager();
    }

    @Override
    public void draw() {
        TextGraphics graphics = screenManager.getTextGraphics();

        graphics.setBackgroundColor(TextColor.Factory.fromString("black"));
        graphics.fill(' ');

        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(10, 2, "===== GAME OVER =====");

        for (int i = 0; i < menu.getOptionsGameOver().size(); i++) {
            if (i == menu.getSelectedOption()) {
                graphics.setForegroundColor(TextColor.ANSI.YELLOW);
                graphics.putString(10, 4 + i, "> " + menu.getOptionsGameOver().get(i));
            } else {
                graphics.setForegroundColor(TextColor.ANSI.WHITE);
                graphics.putString(10, 4 + i, "  " + menu.getOptionsGameOver().get(i));
            }
        }
        graphics.setForegroundColor(TextColor.ANSI.CYAN);
        graphics.putString(2, 13,  " ######      /\\    |\\    /| ########  ");
        graphics.putString(2, 14,  " ##         /  \\   | \\  / | ##        ");
        graphics.putString(2, 15,  " ##  ###   /----\\  |  \\/  | #######  ");
        graphics.putString(2, 16,  " ##   ##  /      \\ |      | ##      ");
        graphics.putString(2, 17,  " ######  /        \\|      | ########  ");
    }


}
