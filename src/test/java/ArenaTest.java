import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tetronix.Arena;
import tetronix.Position;
import tetronix.TetrisBlock;

public class ArenaTest {

    private Arena arena;
    private int columns;
    private int rows;

    @BeforeEach
    public void setUp() {
        columns = 10;
        rows = 20;
        arena = new Arena(columns, rows); // Inicializa a arena com os valores padrão
    }

    @Test
    public void testConstructorInitialization() {
        // Assertions
        Assertions.assertEquals(columns, arena.getBackground()[0].length); // Colunas
        Assertions.assertEquals(rows, arena.getBackground().length); // Linhas
        Assertions.assertTrue(arena.getBackground()[0][0] == null); // Background inicial vazio
    }

    @Test
    public void testMoveBlockToBackground() {
        // Setup
        int[][] shape = {
                {1, 0},
                {1, 1}
        };
        Position position = new Position(4, 5); // Bloco começa em (5, 4)
        TetrisBlock block = new TetrisBlock(shape, "#FF0000", position, columns, rows);

        // Action
        arena.moveBlocktoBackground(block);

        // Assertions
        String[][] background = arena.getBackground();
        Assertions.assertNotNull(background[5][4]); // Parte superior esquerda do bloco
        Assertions.assertNotNull(background[6][4]); // Parte inferior esquerda do bloco
        Assertions.assertNotNull(background[6][5]); // Parte inferior direita do bloco

        Assertions.assertNull(background[5][5]); // Parte superior direita (vazia no bloco)
    }

    @Test
    public void testDraw() {
        // Setup
        TextGraphics graphics = Mockito.mock(TextGraphics.class);

        // Action
        arena.draw(graphics);

        // Verification
        Mockito.verify(graphics, Mockito.times(rows * columns)).fillRectangle(
                Mockito.any(TerminalPosition.class),
                Mockito.any(TerminalSize.class),
                Mockito.eq(' ')
        );
    }
}
