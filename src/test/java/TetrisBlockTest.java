import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TetrisBlockTest {
    @Test
    void testConstructorInitialization() {
        int[][] shape = {{1, 1}, {1, 0}};
        String color = "#FF0000";
        Position position = new Position(0, 0);
        TetrisBlock block = new TetrisBlock(shape, color, position, 10, 20);

        assertArrayEquals(shape, block.getShape());
        assertEquals(color, block.getColor());
        assertEquals(position, block.getPosition());
    }

    @Test
    void testRotation() {
        int[][] shape = {{1, 1}, {1, 0}};
        Position position = new Position(0, 0);
        TetrisBlock block = new TetrisBlock(shape, "#FF0000", position, 10, 20);

        block.rotateBlock();
        int[][] expectedShape = {{1, 1}, {0, 1}};
        assertArrayEquals(expectedShape, block.getShape());
    }


    @Test
    public void testisAtBottomEdge() {
        // Setup
        int[][] shape = {{1, 0}, {1, 0}, {1, 1}};
        Position position = new Position(0, 37);
        TetrisBlock block = new TetrisBlock(shape, "blue", position, 10, 40);

        // Teste
        assertTrue(block.isAtBottomEdge());
    }

    @Test
    public void testDropBlock() {
        // Setup
        int[][] shape = {
                {1, 1},
                {1, 0}
        };
        Position initialPosition = new Position(5, 35); // Começa na linha 35
        TetrisBlock block = new TetrisBlock(shape, "#FF0000", initialPosition, 10, 40); // Grid de altura 40

        // Executa o método dropBlock
        Position finalPosition = block.dropBlock();

        // Verificações
        Assertions.assertEquals(5, finalPosition.getX()); // X deve permanecer o mesmo
        Assertions.assertEquals(40 - shape.length, finalPosition.getY());
    }
}
