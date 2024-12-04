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
        tetrisBlockController = new TetrisBlockController(game_);
    }

    public void processInput(KeyStroke key){
        if(game.getTetris_block() == null) return;

        switch (key.getKeyType()) {
            case ArrowUp:
                System.out.println("ArrowUp pressed!");
                tetrisBlockController.rotateBlock();
                break;
            case ArrowDown:
                System.out.println("ArrowDown pressed!");
                tetrisBlockController.dropBlock(game.getArena()); // passo aqui para fazer dependey injection
                break;
            case ArrowLeft:
                System.out.println("ArrowLeft pressed!");
                game.moveBlock(tetrisBlockController.moveLeft(), KeyType.ArrowLeft);
                break;
            case ArrowRight:
                System.out.println("ArrowRight pressed!");
                game.moveBlock(tetrisBlockController.moveRight(), KeyType.ArrowRight);
                break;
            case Character:
                if (key.getCharacter() == 'q') {
                    System.exit(0);
                }
                break;
            default:
                break;
        }
    }
}
