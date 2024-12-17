package tetronix.Model.BlockType;

import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;

import java.util.Random;

public class JBlock extends TetrisBlock {

    private static Position calculateSpawnPosition(int columns, int[][] shape) {
        Random random = new Random();
        int spawnRow = -shape.length; // Bloco nasce fora do ecrã, acima da arena
        int spawnColumn = generateValidColumn(random , shape, columns);
        return new Position(spawnColumn, spawnRow);
    }

    public JBlock(String color_, int random_rotation, int columns_, int rows_) {
        super(
                new int[][] {
                        {0, 1},
                        {0, 1},
                        {1, 1}
                             },
                color_,
                calculateSpawnPosition(columns_, new int[][] {{0, 1}, {0, 1}, {1, 1}}),
                random_rotation,
                columns_,
                rows_
        );
    }
}
