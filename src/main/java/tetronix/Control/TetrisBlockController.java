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

    public Position moveDown() {
        block = game.getTetris_block();
        if(block == null){
            return null;
        }
        Position current_position = block.getPosition();

        Position new_position = new Position(current_position.getColumn_identifier(),current_position.getRow_identifier() + 1);
        return new_position;
    }

    public Position moveLeft() {
        block = game.getTetris_block();
        if(block == null) return null;
        Position current_position = block.getPosition();

        Position new_position = new Position(current_position.getColumn_identifier() - 2,current_position.getRow_identifier());
        return new_position;

    }

    public Position moveRight() {
        block = game.getTetris_block();
        if(block == null) return null;
        Position current_position = block.getPosition();

        Position new_position = new Position(current_position.getColumn_identifier() + 2,current_position.getRow_identifier());
        return new_position;

    }

    public void rotateBlock() {
        block = game.getTetris_block();
        if(block == null) return;
        int new_rotation = block.getCurrent_rotation() + 1;
        if(new_rotation > 3) new_rotation = 0;

        if(!game.getArena().canRotate(block,new_rotation)){
            return;
        }

        //solução para o blocoI
        if(block.getShape().length == 1){
            Position position1 = new Position(block.getPosition().getColumn_identifier() + 2,block.getPosition().getRow_identifier() - 1);
            block.setPosition(position1);
        }

        if(block.getShape().length == 4){
            Position position1 = new Position(block.getPosition().getColumn_identifier() - 2,block.getPosition().getRow_identifier() + 1);
            block.setPosition(position1);
        }
        block.setCurrent_rotation(new_rotation);


        if(game.getArena().isBlockOutBoundsAfterRotation(block)){
            block.CorrectPositionAfterRotation();
        }
    }
}
