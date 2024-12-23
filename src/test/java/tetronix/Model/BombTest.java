package tetronix.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetronix.Model.Arena;
import tetronix.Model.Bomb;
import tetronix.Model.BombFactory;
import tetronix.Model.Position;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BombTest {

    private Bomb bomb;
    private Arena mockArena;
    private String[][] grid;

    @BeforeEach
    void setUp() {
        mockArena = mock(Arena.class);
        when(mockArena.getColumns()).thenReturn(10);
        when(mockArena.getRows()).thenReturn(20);
        when(mockArena.getHighestOccupiedRow()).thenReturn(5);

        bomb = new Bomb("red", new Position(5, 5), 10, 20);
        grid = new String[20][10];
    }

    @Test
    void testBombCreation() {
        assertNotNull(bomb);
        assertEquals("red", bomb.getColor());
        assertEquals(5, bomb.getPosition().getRow_identifier());
        assertEquals(5, bomb.getPosition().getColumn_identifier());
    }

    @Test
    void testBombExpiration() {
        assertFalse(bomb.isExpired());

        // Simulate time passing
        try {
            Thread.sleep(5100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(bomb.isExpired());
    }

    @Test
    void testUpdateBombPosition() {
        bomb.updateBombPosition(bomb, 7, 7);
        assertEquals(7, bomb.getPosition().getRow_identifier());
        assertEquals(7, bomb.getPosition().getColumn_identifier());
    }

   /* @Test
    void testBombExplode() {
        // Set up some blocks around the bomb
        for (int i = 4; i <= 6; i++) {
            for (int j = 8; j <= 12; j++) {
                grid[i][j] = "block";
            }
        }

        bomb.explode(grid);

        // Check if surrounding blocks are cleared
        for (int i = 4; i <= 6; i++) {
            for (int j = 8; j <= 12; j++) {
                assertNull(grid[i][j]);
            }
        }

        // Check if blocks outside explosion radius are untouched
        assertNotNull(grid[3][7]);
        assertNotNull(grid[7][13]);
    }*/

    @Test
    void testBombFactoryCreateBomb() {
        Bomb createdBomb = BombFactory.createBomb(mockArena);
        assertNotNull(createdBomb);
        assertTrue(createdBomb.getPosition().getRow_identifier() <= 5);
        assertTrue(createdBomb.getPosition().getColumn_identifier() < 10);
    }
}
