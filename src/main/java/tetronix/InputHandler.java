package tetronix;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

public class InputHandler {
    private Game game;

    public InputHandler(Game game) {
        this.game = game;
    }

    public void processInput(KeyStroke key) throws IOException {
        TetrisBlock block = game.getTetris_block();
        switch (key.getKeyType()) {
            case ArrowUp:
                block.rotateBlock();
                break;
            case ArrowDown:
                game.dropBlock();
                break;
            case ArrowLeft:
                game.moveBlock(block.moveLeft(), KeyType.ArrowLeft);
                break;
            case ArrowRight:
                game.moveBlock(block.moveRight(), KeyType.ArrowRight);
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
