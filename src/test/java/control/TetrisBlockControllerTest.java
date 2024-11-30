package control;   //tetronix control

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetronix.control.TetrisBlockController;
import tetronix.model.Arena;
import tetronix.model.Position;
import tetronix.model.TetrisBlock;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TetrisBlockControllerTest {
    private TetrisBlockController blockControl;
    private TetrisBlock block;
    private Arena mockArena;
    private Position startPosition;
    private int[][] shape = {
            {1, 0},
            {1, 1}
    };
    private int columns = 10;
    private int rows = 20;

    @BeforeEach
    void setUp() {
        // Cria um mock para a Arena
        mockArena = mock(Arena.class);
        // Inicializa um bloco na posição (0, 0)
        startPosition = new Position(0, 0);
        block = new TetrisBlock(shape, "#FF0000", startPosition);
    }

    @Test
    void testRotateBlock() {
        // Initial shape before rotation
        int[][] initialShape = block.getShape();

        // Rotate the block using the controller
        blockControl.rotateBlock();

        // Get the new shape after rotation
        int[][] rotatedShape = block.getShape();

        assertNotNull(rotatedShape, "The shape should not be null after rotation.");
        assertNotEquals(initialShape, rotatedShape, "The shape should change after rotation.");
        assertEquals(initialShape[0].length, rotatedShape.length, "The rotated shape's rows should equal the original shape's columns.");
        assertEquals(initialShape.length, rotatedShape[0].length, "The rotated shape's columns should equal the original shape's rows.");
    }

    @Test
    void testCanMoveDown_WhenNotAtBottomAndNoCollision() {
        when(mockArena.getBackground()).thenReturn(new String[rows][columns]);
        block.setPosition(new Position(0, 0));

        boolean result = mockArena.canMoveDown(block);

        assertTrue(result, "The block should be able to move down.");
    }

    @Test
    void testCanMoveDown_WhenAtBottomEdge() {
        block.setPosition(new Position(0, rows - shape.length));

        boolean result = mockArena.canMoveDown(block);

        assertFalse(result, "The block should not be able to move down at the bottom edge.");
    }

    @Test
    void testCanMoveDown_WhenCollisionBelow() {
        String[][] background = new String[rows][columns];
        background[2][0] = "#FFFFFF"; // Simula um bloco na célula diretamente abaixo
        when(mockArena.getBackground()).thenReturn(background);

        block.setPosition(new Position(0, 0));

        boolean result = mockArena.canMoveDown(block);

        assertFalse(result, "The block should not be able to move down due to a collision below.");
    }

    @Test
    void testCanMoveLeft_WhenNotAtLeftEdgeAndNoCollision() {
        // Configurar uma arena sem colisões
        when(mockArena.getBackground()).thenReturn(new String[rows][columns]);
        block.setPosition(new Position(1, 0)); // Não na borda esquerda

        boolean result = mockArena.canMoveLeft(block);

        assertTrue(result, "The block should be able to move left when not at the left edge and no collision.");
    }

    @Test
    void testCanMoveLeft_WhenAtLeftEdge() {
        // Configurar a posição na borda esquerda
        block.setPosition(new Position(0, 0)); // Na borda esquerda

        boolean result = mockArena.canMoveLeft(block);

        assertFalse(result, "The block should not be able to move left when at the left edge.");
    }



}
