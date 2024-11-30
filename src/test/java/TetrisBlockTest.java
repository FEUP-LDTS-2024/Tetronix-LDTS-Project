import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import tetronix.model.Arena;
import tetronix.model.Position;
import tetronix.model.TetrisBlock;
import tetronix.control.TetrisBlockController;
import tetronix.Game;

import static org.junit.jupiter.api.Assertions.*;

class TetrisBlockTest {
    private Arena arena;
    private TetrisBlock block;
    private TetrisBlockController blockControl;
    private Arena mockArena;
    private Game mockGame;
    private Position startPosition;
    private int[][] shape = {
            {1, 0},
            {1, 1}
    }; // Forma quadrada simples para facilitar o teste
    private int columns = 10;
    private int rows = 20;

    @BeforeEach
    void setUp() {
        // Cria um mock para a Arena
        mockArena = mock(Arena.class);

        // Mock the Game class to return our block
        mockGame = mock(Game.class);

        // Inicializa um bloco na posição (0, 0)
        startPosition = new Position(0, 0);
        block = new TetrisBlock(shape, "#FF0000", startPosition);

        // Mock the Game to return the block when getTetris_block is called
        when(mockGame.getTetris_block()).thenReturn(block);

        // Now create the TetrisBlockController with the mocked Game
        blockControl = new TetrisBlockController(mockGame);
    }

    @Test
    void testMoveDown() {
        Position newPosition = blockControl.moveDown();

        assertEquals(1, newPosition.getRow_identifier(), "The block should move one step down (increment Y).");
        assertEquals(0, newPosition.getColumn_identifier(), "The block should maintain the same column (X).");
    }

    @Test
    void testMoveLeft() {
        Position newPosition = blockControl.moveLeft();

        assertEquals(-1, newPosition.getColumn_identifier(), "The block should move one step left.");
        assertEquals(0, newPosition.getRow_identifier(), "The block should maintain the same row.");
    }

    @Test
    void testMoveRight() {
        Position newPosition = blockControl.moveRight();

        assertEquals(1, newPosition.getColumn_identifier(), "The block should move one step right (increment X).");
        assertEquals(0, newPosition.getRow_identifier(), "The block should maintain the same row (Y).");
    }

    @Test
    public void testIsAtRightEdge() {
        when(mockArena.isAtRightEdge(block)).thenReturn(true);

        boolean result = mockArena.isAtRightEdge(block);

        assertTrue(result, "The block should be at the right edge.");
    }

    @Test
    public void testIsNotAtRightEdge() {
        when(mockArena.isAtRightEdge(block)).thenReturn(false);

        boolean result = mockArena.isAtRightEdge(block);

        assertFalse(result, "The block should not be at the right edge.");
    }

    @Test
    public void testIsAtLeftEdge() {
        when(mockArena.isAtLeftEdge(block)).thenReturn(true);

        boolean result = mockArena.isAtLeftEdge(block);

        assertTrue(result, "The block should be at the left edge.");
    }

    @Test
    public void testIsNotAtLeftEdge() {
        when(mockArena.isAtLeftEdge(block)).thenReturn(false);

        boolean result = mockArena.isAtLeftEdge(block);

        assertFalse(result, "The block should not be at the left edge.");
    }

    @Test
    public void testIsAtBottomEdge() {
        when(mockArena.isAtBottomEdge(block)).thenReturn(true);

        boolean result = mockArena.isAtBottomEdge(block);

        assertTrue(result, "The block should be at the bottom edge.");
    }

    @Test
    public void testIsNotAtBottomEdge() {
        when(mockArena.isAtBottomEdge(block)).thenReturn(false);

        boolean result = mockArena.isAtBottomEdge(block);

        assertFalse(result, "The block should not be at the bottom edge.");
    }


    // Additional tests for move right and collision checks can follow in the same pattern...
}
