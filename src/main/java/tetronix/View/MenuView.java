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
        graphics.putString(2, 2, "============= MAIN MENU =============");


        for (int i = 0; i < menu.getOptions().size(); i++) {
            if (i == menu.getSelectedOption()) {
                graphics.setForegroundColor(TextColor.ANSI.YELLOW);
                graphics.putString(10, 6 + i, "> " + menu.getOptions().get(i));
            } else {
                graphics.setForegroundColor(TextColor.ANSI.WHITE);
                graphics.putString(10, 6 + i, "  " + menu.getOptions().get(i));
            }
        }


        graphics.setForegroundColor(TextColor.ANSI.CYAN);
        graphics.putString(0, 13, " ### #### ### ###    #   #  # ### #   #");
        graphics.putString(0, 14, "  #  #     #  #  # #  #  ## #  #   # #");
        graphics.putString(0, 15, "  #  ###   #  ###  #   # ####  #    #");
        graphics.putString(0, 16, "  #  #     #  # #  #   # # ##  #   # #");
        graphics.putString(0, 17, "  #  ####  #  #  #  ##   #  # ### #   #");




    }
}
