package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tetronix.Game;

import java.io.IOException;

public class InputHandlerForGame implements InputHandler {
    private Game game;
    private TetrisBlockController tetrisBlockController;

    public InputHandlerForGame(Game game_) {
        this.game = game_;
    }

    @Override
    public void processInput(KeyStroke key){
        if(game.getTetris_block() == null) return;

        this.tetrisBlockController = game.getTetrisBlockController();

        switch (key.getKeyType()) {
            case ArrowUp:
                tetrisBlockController.rotateBlock();
                break;
            case ArrowDown:
                game.moveBlock(tetrisBlockController.moveDown(), KeyType.ArrowDown);
                break;
            case ArrowLeft:
                game.moveBlock(tetrisBlockController.moveLeft(), KeyType.ArrowLeft);
                break;
            case ArrowRight:
                game.moveBlock(tetrisBlockController.moveRight(), KeyType.ArrowRight);
                break;
            default:
                break;
        }
    }
}
