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
    private int rows;
    private int columns;
    public ScreenManager(int rows_, int columns_ ) throws IOException {
        // Criação do terminal
        this.columns = columns_;
        this.rows = rows_;
        TerminalSize terminalSize = new TerminalSize(columns_, rows_);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        Terminal terminal = terminalFactory.createTerminal();

        // Criação da tela
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null); // Desabilitar o cursor
        screen.startScreen(); // Iniciar a tela
    }

    public int getColumns() {return columns;}
    public int getRows(){return rows;}
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
