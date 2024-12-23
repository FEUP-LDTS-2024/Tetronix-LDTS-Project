package tetronix.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Model.Menu;

import java.util.List;

public class MenuStatisticsView implements ElementViewer<Menu> {
    private final Menu menu;
    private final ScreenManager screenManager;

    public MenuStatisticsView(Menu menu_) {
        this.menu = menu_;
        this.screenManager = menu_.getScreenManager();
    }

    @Override
    public void draw() {
        // Obtém os scores registrados
        List<Integer> scores = menu.getTrack_Scores();

        // Define os textos de título e instruções
        String text = "========GAME SCORES========";
        String instructions1 = "Press 'N' for a new game";
        String instructions2 = "Press 'ESC' to exit";

        TextGraphics graphics = screenManager.getTextGraphics();

        graphics.setBackgroundColor(TextColor.Factory.fromString("black"));
        graphics.fill(' ');

        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(5, 2, text);


        int startRow = 4;
        for (int i = 0; i < scores.size(); i++) {
            String scoreText = "Game " + (i + 1) + ": " + scores.get(i);
            graphics.putString(10, startRow, scoreText);
            startRow++;
        }


        // Exibe as instruções
        graphics.putString(5, startRow+1, instructions1);
        graphics.putString(5, startRow+2, instructions2);
    }
}
