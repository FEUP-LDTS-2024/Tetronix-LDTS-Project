package tetronix.View;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Model.Menu;

import java.util.List;

public class MenuStatisticsView implements ElementViewer<Menu>{
    private Menu menu;
    private ScreenManager screenManager;

    public MenuStatisticsView(Menu menu_){
        this.menu = menu_;
        this.screenManager = menu_.getScreenManager();
    }

    @Override
    public void draw() {

        List<Integer> scores = menu.getTrack_Scores();
        String text = "========GAME SCORES========";
        String instructions1 = "Press 'N' for a new game";
        String instructions2 = "Press 'ESC' to exit";
        int screenWidth = screenManager.getColumns();
        TextGraphics graphics = screenManager.getTextGraphics();


        graphics.setBackgroundColor(TextColor.Factory.fromString("white"));
        graphics.fill(' ');

        graphics.setForegroundColor(TextColor.Factory.fromString("black"));

        int y = (screenWidth - text.length()) / 2;
        int x = 2;
        graphics.putString(y, x, text);

        int y_instr1 = screenWidth - instructions1.length();
        int y_instr2 = screenWidth - instructions2.length();
        graphics.putString(y_instr1, 4, instructions1);
        graphics.putString(y_instr2, 5, instructions2);


        int row_ = 3;
        for (int i = 0; i < scores.size(); i++) {
            graphics.putString(0, row_, "Game " + (i + 1) + ": " + scores.get(i));
            row_++;
        }
    }
}
