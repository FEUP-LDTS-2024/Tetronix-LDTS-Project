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
            block = TetrisBlockFactory.createBlock(game.getColumns(),game.getRows());
            game.setTetris_block(block);
            while(game.continuousBlockFall(block.moveDown())){
                try {
                    game.renderImage();
                    Thread.sleep(350); // Aguarda 1 segundo
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
