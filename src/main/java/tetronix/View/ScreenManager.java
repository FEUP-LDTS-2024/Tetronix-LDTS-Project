package tetronix.View;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import tetronix.Game;

import java.io.IOException;

public class ScreenManager {
    private Screen screen;

    public ScreenManager(Game game) throws IOException {
        // Criação do terminal
        TerminalSize terminalSize = new TerminalSize(game.getColumns(), game.getRows());
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        Terminal terminal = terminalFactory.createTerminal();

        // Criação da tela
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null); // Desabilitar o cursor
        screen.startScreen(); // Iniciar a tela
    }

    public void clear() {
        screen.clear();
    }

    public void refresh() throws IOException {
        screen.refresh();
    }

    public TextGraphics getTextGraphics() {
        return screen.newTextGraphics();
    }

    public void close() throws IOException {
        screen.close();
    }

    public KeyStroke readInput() throws IOException {
        return screen.readInput();
    }


}
