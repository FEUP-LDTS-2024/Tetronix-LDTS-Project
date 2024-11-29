package control;   //tetronix control

import org.junit.jupiter.api.BeforeEach;
import tetronix.model.Arena;
import tetronix.model.Position;
import tetronix.model.TetrisBlock;

import static org.mockito.Mockito.mock;

public class TetrisBlockControllerTest {
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




}
