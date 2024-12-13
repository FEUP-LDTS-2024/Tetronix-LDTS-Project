package tetronix.View;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Model.Menu;

public class MenuView implements ElementViewer<Menu> {
    private final Menu menu;
    private final ScreenManager screenManager;

    public MenuView(Menu menu) {
        this.menu = menu;
        this.screenManager = menu.getScreenManager();
    }

    @Override
    public void draw() {
        TextGraphics graphics = screenManager.getTextGraphics();


        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(10, 2, "===== MAIN MENU =====");

        // Exibe as opções do menu
        for (int i = 0; i < menu.getOptions().size(); i++) {
            if (i == menu.getSelectedOption()) {
                graphics.setForegroundColor(TextColor.ANSI.YELLOW); // Destaque para a opção selecionada
                graphics.putString(10, 4 + i, "> " + menu.getOptions().get(i));
            } else {
                graphics.setForegroundColor(TextColor.ANSI.WHITE);
                graphics.putString(10, 4 + i, "  " + menu.getOptions().get(i));
            }
        }
    }
}
