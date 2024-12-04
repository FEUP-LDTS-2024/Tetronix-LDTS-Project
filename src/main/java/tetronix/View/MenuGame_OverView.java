package tetronix.View;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Model.Menu;

public class MenuGame_OverView implements ElementViewer<Menu> {
    private ScreenManager screenManager;

    public MenuGame_OverView(ScreenManager screenManager_) {
        this.screenManager = screenManager_;
    }

    @Override
    public void draw() {
        // Configurações do texto
        String gameOverText = "GAME OVER";
        String instructions1 = "Press 'N' for a new game";
        String instructions2 = "Press 'S' for statistics";
        String instructions3 = "Press 'ESC' to exit";

        TextGraphics graphics = screenManager.getTextGraphics();

        // Desenha o retângulo vermelho
        graphics.setBackgroundColor(TextColor.Factory.fromString("red"));
        graphics.fillRectangle(new TerminalPosition(2, 5), new TerminalSize(30, 10), ' ');

        // Define o texto com cor preta
        graphics.setForegroundColor(TextColor.Factory.fromString("black"));
        graphics.enableModifiers(SGR.BOLD); // Opcional: torna o texto em negrito

        // Escreve os textos dentro do retângulo
        graphics.putString(5, 6, gameOverText);
        graphics.putString(5, 8, instructions1);
        graphics.putString(5, 9, instructions2);
        graphics.putString(5, 10, instructions3);
    }
}


