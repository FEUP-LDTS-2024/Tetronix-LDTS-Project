import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;



public class GameThread extends Thread {
    private Game game;

    public GameThread(Game game_) {
        this.game = game_;
    }

    public void run() {
        try {
            TetrisBlock block = game.spawnBlocks(); // Cria o bloco uma vez
            while (true) {
                game.moveBlockDown(block.moveDown());
                game.draw();
                Thread.sleep(125); // Aguarda 1 segundo
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
