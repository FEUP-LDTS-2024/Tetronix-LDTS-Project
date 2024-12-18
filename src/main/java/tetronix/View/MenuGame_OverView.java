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

        // Limpa a tela com fundo preto
        graphics.setBackgroundColor(TextColor.Factory.fromString("black"));
        graphics.fill(' ');

        // Título do menu
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(10, 2, "===== GAME OVER =====");

        // Exibe as opções do menu de Game Over
        for (int i = 0; i < menu.getOptionsGameOver().size(); i++) {
            if (i == menu.getSelectedOption()) {
                // Destaca a opção selecionada em amarelo
                graphics.setForegroundColor(TextColor.ANSI.YELLOW);
                graphics.putString(10, 4 + i, "> " + menu.getOptionsGameOver().get(i));
            } else {
                // Exibe as demais opções em branco
                graphics.setForegroundColor(TextColor.ANSI.WHITE);
                graphics.putString(10, 4 + i, "  " + menu.getOptionsGameOver().get(i));
            }
        }
    }
}
