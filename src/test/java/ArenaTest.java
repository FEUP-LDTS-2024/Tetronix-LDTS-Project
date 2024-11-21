import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class ArenaTest {

    @Test
    public void testConstructorInitialization() {
        // Setup
        int columns = 10;
        int rows = 20;

        // Test
        Arena arena = new Arena(columns, rows);

        // Assertions
        Assertions.assertEquals(columns, arena.getBackground()[0].length); // Colunas
        Assertions.assertEquals(rows, arena.getBackground().length); // Linhas
        Assertions.assertTrue(arena.getBackground()[0][0] == null); // Background inicial vazio
    }

    @Test
    public void testMoveBlockToBackground() {
        // Setup
        Arena arena = new Arena(10, 20);
        int[][] shape = {
                {1, 0},
                {1, 1}
        };
        Position position = new Position(4, 5); // Bloco começa em (4, 5)
        TetrisBlock block = new TetrisBlock(shape, "#FF0000", position, 10, 20);

        // Action
        arena.moveBlocktoBackground(block);

        // Assertions
        String[][] background = arena.getBackground();
        Assertions.assertNotNull(background[4][5]); // Parte superior esquerda do bloco
        Assertions.assertNotNull(background[5][5]); // Parte inferior esquerda do bloco
        Assertions.assertNotNull(background[5][6]); // Parte inferior direita do bloco

        Assertions.assertNull(background[4][6]); // Parte superior direita (vazia no bloco)
    }

    @Test
    public void testDraw() {
        // Setup
        Arena arena = new Arena(10, 20);
        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        // Action
        arena.draw(graphics);

        // Verification
        Mockito.verify(graphics, Mockito.times(200)).fillRectangle(
                Mockito.any(TerminalPosition.class),
                Mockito.any(TerminalSize.class),
                Mockito.eq(' ')
        );
    }
}
