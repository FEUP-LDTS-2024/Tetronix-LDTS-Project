package tetronix.View;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;


public class MenuView {

    public Screen screen;

    public MenuView(Screen screen_){
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

        TextGraphics sg =screen.newTextGraphics();

        sg.putString(7, 10, "████████╗███████╗████████╗██████╗  ██████╗ ███╗   ██╗██╗██╗  ██╗", SGR.BOLD);
        sg.putString(7, 11, "╚══██╔══╝██╔════╝╚══██╔══╝██╔══██╗██╔═══██╗████╗  ██║██║██║ ██╔╝", SGR.BOLD);
        sg.putString(7, 12, "   ██║   █████╗     ██║   ██████╔╝██║   ██║██╔██╗ ██║██║ ╚╔██╔╝ ", SGR.BOLD);
        sg.putString(7, 13, "   ██║   ██╔══╝     ██║   ██ ██══╝ ██║   ██║██║╚██╗██║██║██═██╗ ", SGR.BOLD);
        sg.putString(7, 14, "   ██║   ███████╗   ██║   ██║ ██║ ╚██████╔╝██║ ╚████║██║██║  ██╗", SGR.BOLD);
        sg.putString(7, 15, "   ╚═╝   ╚══════╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═╝", SGR.BOLD);


        refresh();
    }


}
