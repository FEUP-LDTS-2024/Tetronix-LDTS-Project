import com.googlecode.lanterna.input.KeyStroke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tetronix.*;

import static org.mockito.Mockito.*;

import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TetrisBlockTest {
    private TetrisBlock block;
    private Arena mockArena;
    private Position startPosition;
    private int[][] shape = {
            {1, 1},
            {1, 1}
    }; // Forma quadrada simples para facilitar o teste
    private int columns = 10;
    private int rows = 20;

    @BeforeEach
    void setUp() {
        // Cria um mock para a Arena
        mockArena = mock(Arena.class);
        // Inicializa um bloco na posição (0, 0)
        startPosition = new Position(0, 0);
        block = new TetrisBlock(shape, "#FF0000", startPosition, columns, rows);
    }

    @Test
    void testMoveDown() {

        Position newPosition = block.moveDown();


        assertEquals(1, newPosition.getRow_identifier(), "The block should move one step down (increment Y).");
        assertEquals(0, newPosition.getColumn_identifier(), "The block should maintain the same column (X).");
    }

    @Test
    void testMoveLeft() {

        Position newPosition = block.moveLeft();


        assertEquals(-1, newPosition.getColumn_identifier(), "The block should move one step left .");
        assertEquals(0, newPosition.getRow_identifier(), "The block should maintain the same row.");
    }

    @Test
    void testMoveRight() {

        Position newPosition = block.moveRight();


        assertEquals(1, newPosition.getColumn_identifier(), "The block should move one step right (increment X).");
        assertEquals(0, newPosition.getRow_identifier(), "The block should maintain the same row (Y).");
    }

    @Test
    void testIsAtRightEdge() {

        block.setPosition(new Position(columns - shape[0].length, 0));


        boolean result = block.isAtRightEdge();


        assertTrue(result, "The block should be at the right edge.");
    }

    @Test
    void testIsNotAtRightEdge() {

        block.setPosition(new Position(columns - shape[0].length - 1, 0));


        boolean result = block.isAtRightEdge();


        assertFalse(result, "The block should not be at the right edge.");
    }

    @Test
    void testIsAtLeftEdge() {

        block.setPosition(new Position(0, 0));


        boolean result = block.isAtLeftEdge();


        assertTrue(result, "The block should be at the left edge.");
    }

    @Test
    void testIsNotAtLeftEdge() {

        block.setPosition(new Position(1, 0));


        boolean result = block.isAtLeftEdge();


        assertFalse(result, "The block should not be at the left edge.");
    }

    @Test
    void testIsAtBottomEdge() {

        block.setPosition(new Position(0, rows - shape.length));


        boolean result = block.isAtBottomEdge();


        assertTrue(result, "The block should be at the bottom edge.");
    }

    @Test
    void testIsNotAtBottomEdge() {

        block.setPosition(new Position(0, rows - shape.length - 1));


        boolean result = block.isAtBottomEdge();


        assertFalse(result, "The block should not be at the bottom edge.");
    }

    @Test
    void testRotateBlock() {

        block.rotateBlock();


        int[][] rotatedShape = block.getShape();
        assertNotNull(rotatedShape, "The shape should not be null after rotation.");
        assertEquals(shape[0].length, rotatedShape.length, "The rotated shape's rows should equal the original shape's columns.");
        assertEquals(shape.length, rotatedShape[0].length, "The rotated shape's columns should equal the original shape's rows.");
    }

    @Test
    void testCanMoveDown_WhenNotAtBottomAndNoCollision() {

        when(mockArena.getBackground()).thenReturn(new String[rows][columns]);
        block.setPosition(new Position(0, 0));


        boolean result = block.canMoveDown(mockArena);


        assertTrue(result, "The block should be able to move down.");
    }

    @Test
    void testCanMoveDown_WhenAtBottomEdge() {

        block.setPosition(new Position(0, rows - shape.length));


        boolean result = block.canMoveDown(mockArena);


        assertFalse(result, "The block should not be able to move down at the bottom edge.");
    }

    @Test
    void testCanMoveDown_WhenCollisionBelow() {

        String[][] background = new String[rows][columns];
        //O bloco é um quadrado de lado 2, se ele na matrix estiver na posição 0, as posições [0,0],[0,1],[1,0],[1,1] vão estar ocupadas
        //por isso, para verificar é preciso ver se o bloco desce quando a linha de índice 2 (3ºlinha)[2,0] está pintada
        background[2][0] = "#FFFFFF"; // Simula um bloco na célula diretamente abaixo
        when(mockArena.getBackground()).thenReturn(background);

        block.setPosition(new Position(0, 0));


        boolean result = block.canMoveDown(mockArena);


        assertFalse(result, "The block should not be able to move down due to a collision below.");
    }

    @Test
    void testIsNextDownPositionOccupied_WhenNoCollision() {

        String[][] background = new String[rows][columns];
        when(mockArena.getBackground()).thenReturn(background);

        block.setPosition(new Position(0, 0));


        boolean result = block.isNextDownPositonOccupied(mockArena);


        assertFalse(result, "The next position below should not be occupied.");
    }

    @Test
    void testIsNextDownPositionOccupied_WhenCollisionDetected() {

        String[][] background = new String[rows][columns];
        background[2][0] = "#FFFFFF"; // Simula um bloco na célula diretamente abaixo
        when(mockArena.getBackground()).thenReturn(background);

        block.setPosition(new Position(0, 0));


        boolean result = block.isNextDownPositonOccupied(mockArena);


        assertTrue(result, "The next position below should be occupied.");
    }
}

