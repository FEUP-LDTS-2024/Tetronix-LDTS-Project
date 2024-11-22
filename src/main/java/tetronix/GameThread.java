package tetronix;

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
                    Thread.sleep(125); // Aguarda 1 segundo
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
