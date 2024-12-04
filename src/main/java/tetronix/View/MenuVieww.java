package tetronix.View;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import tetronix.Game;
import tetronix.Model.Menu;

import java.io.IOException;


public class MenuVieww {

    public Screen screen;

    public MenuVieww(Screen screen_){
        this.screen=screen_;
    }
    public void refresh() throws IOException {
        screen.refresh();
    }
    public void clear()throws IOException{
        screen.clear();
    }
    public TextGraphics getTextGraphics(){
        return  screen.newTextGraphics();
    }
    public void close() throws IOException {
        screen.close();
    }
    public KeyStroke readInput() throws IOException {
        return screen.readInput();
    }
    public void visor(String[] options, int selectedOption) throws IOException {
        // Limpa a tela antes de desenhar
        clear();

        TextGraphics tg = screen.newTextGraphics();

        // Título do menu
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(10, 2, "===== MAIN MENU =====");

        // Exibe cada opção do menu em uma linha diferente
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                tg.setForegroundColor(TextColor.ANSI.YELLOW); // Destaca a opção selecionada
                tg.putString(10, 4 + i, "> " + options[i]); // Linha para cada opção
            } else {
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(10, 4 + i, "  " + options[i]); // Opção não selecionada
            }
        }

        refresh();
    }


}
