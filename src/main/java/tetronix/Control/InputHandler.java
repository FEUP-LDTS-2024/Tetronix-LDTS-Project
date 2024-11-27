package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tetronix.Game;
import tetronix.Model.TetrisBlock;

import java.io.IOException;

public class InputHandler {
    private Game game;
    private TetrisBlockController tetrisBlockController;

    public InputHandler(Game game) {
        this.game = game;
        tetrisBlockController = new TetrisBlockController();
    }

    public void processInput(KeyStroke key) throws IOException {
        if(game.getTetris_block() == null) return;

        TetrisBlock block = game.getTetris_block();
        switch (key.getKeyType()) {
            case ArrowUp:
                System.out.println("ArrowUp pressed!");
                tetrisBlockController.rotateBlock(block);
                break;
            case ArrowDown:
                System.out.println("ArrowDown pressed!");
                tetrisBlockController.dropBlock(block,game.getArena());
                break;
            case ArrowLeft:
                System.out.println("ArrowLeft pressed!");
                game.moveBlock(tetrisBlockController.moveLeft(block), KeyType.ArrowLeft);
                break;
            case ArrowRight:
                System.out.println("ArrowRight pressed!");
                game.moveBlock(tetrisBlockController.moveRight(block), KeyType.ArrowRight);
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
