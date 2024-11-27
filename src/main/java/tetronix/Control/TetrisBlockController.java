package tetronix.Control;

import tetronix.Model.Arena;
import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;

public class TetrisBlockController {

    public void dropBlock(TetrisBlock block,Arena arena) {
        // Enquanto o bloco não atingir o fundo ou não houver espaço ocupado abaixo dele
        while(arena.canMoveDown(block)){

            block.setPosition(moveDown(block));
        }
        // Após o bloco chegar ao fundo ou ser bloqueado, movê-lo para a arena
        arena.moveBlocktoBackground(block);
    }


    public Position moveDown(TetrisBlock block) {
        if(block == null) return null;
        Position current_position = block.getPosition();

        Position new_position = new Position(current_position.getColumn_identifier(),current_position.getRow_identifier() + 1);
        return new_position;
    }

    public Position moveLeft(TetrisBlock block) {
        if(block == null) return null;
        Position current_position = block.getPosition();

        Position new_position = new Position(current_position.getColumn_identifier() - 1,current_position.getRow_identifier());
        return new_position;

    }

    public Position moveRight(TetrisBlock block) {
        if(block == null) return null;
        Position current_position = block.getPosition();

        Position new_position = new Position(current_position.getColumn_identifier() + 1,current_position.getRow_identifier());
        return new_position;

    }

    public void rotateBlock(TetrisBlock block) {
        if(block == null) return;
        int[][][] possible_shapes = block.getPossible_shapes();
        int new_rotation = block.getCurrent_rotation() + 1;

        if(new_rotation > 3) new_rotation = 0;
        int [][] new_shape = possible_shapes[new_rotation];
        block.setShape(new_shape);
        block.setCurrent_rotation(new_rotation);
    }
}
