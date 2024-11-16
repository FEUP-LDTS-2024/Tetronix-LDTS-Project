import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    private Arena arena;
    private Figure Tetris_block;
    private int rows = 40;
    private int columns = 40;
    private Figure tetris_block;

    public Game(){
        try {
            // Criação do terminal
            TerminalSize terminalSize = new TerminalSize(columns, rows);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            // Criação da tela
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // Desabilitar o cursor
            screen.startScreen(); // Iniciar a tela

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Desnhar na tela
    private void draw() throws IOException{
        arena = new Arena(columns,rows);
        Tetris_block = new Figure(columns,rows);

        screen.clear();
        arena.draw(screen.newTextGraphics());
        Tetris_block.draw(screen.newTextGraphics());
        screen.refresh();
    }

    public void run() throws IOException{
        this.draw();
    }
}
