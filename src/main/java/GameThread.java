import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;



public class GameThread extends Thread {
    private Game game;
    TetrisBlock block;

    public GameThread(Game game_) {
        this.game = game_;
    }

    public void run(){

        while(true){
            block = game.spawnBlocks();

            while(game.continuousBlockFall(block.moveDown())){
                try {
                    game.draw();
                    Thread.sleep(1000); // Aguarda 1 segundo
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
