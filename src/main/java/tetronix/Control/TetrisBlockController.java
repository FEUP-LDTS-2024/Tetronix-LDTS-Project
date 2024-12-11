package tetronix.Control;

import tetronix.Game;
import tetronix.Model.Arena;
import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;

public class TetrisBlockController {
    private Game game;
    private TetrisBlock block;

    public TetrisBlockController(Game game_){
        this.game = game_;
    }

    public void dropBlock(Arena arena) {
        block = game.getTetris_block();
        while(arena.canMoveDown(block)){
            block.setPosition(moveDown());
        }
        arena.moveBlocktoBackground(block);
    }

    public Position moveDown() {
        block = game.getTetris_block();
        if(block == null) return null;
        Position current_position = block.getPosition();
        return new Position(current_position.getColumn_identifier(), current_position.getRow_identifier() + 1);
    }

    public Position moveLeft() {
        block = game.getTetris_block();
        if(block == null) return null;
        Position current_position = block.getPosition();
        return new Position(current_position.getColumn_identifier() - 2, current_position.getRow_identifier());
    }

    public Position moveRight() {
        block = game.getTetris_block();
        if(block == null) return null;
        Position current_position = block.getPosition();
        return new Position(current_position.getColumn_identifier() + 2, current_position.getRow_identifier());
    }

    public void rotateBlock() {
        block = game.getTetris_block();
        if(block == null) return;
        int new_rotation = (block.getCurrent_rotation() + 1) % 4;

        if(!game.getArena().canRotate(block, new_rotation)){
            return;
        }

        block.setCurrent_rotation(new_rotation);

        if(game.getArena().isBlockOutBoundsAfterRotation(block)){
            block.CorrectPositionAfterRotation();
        }
    }
}
