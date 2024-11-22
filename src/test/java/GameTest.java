import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tetronix.Arena;
import tetronix.Game;
import tetronix.Position;
import tetronix.TetrisBlock;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameTest {
    @Test
    public void testSpawnBlocks() { //to be modified
        Game game = new Game();
        TetrisBlock block = game.spawnBlocks();

        // Verifica se o bloco foi gerado corretamente
        Assertions.assertNotNull(block);
        Assertions.assertEquals(-3, block.getPosition().getRow_identifier()); // A altura do shape é 3
        Assertions.assertEquals(19, block.getPosition().getColumn_identifier()); // Centralizado na arena de 40 colunas
        Assertions.assertArrayEquals(new int[][]{
                {1, 0},
                {1, 0},
                {1, 1}
        }, block.getShape());
        Assertions.assertEquals("#990000", block.getColor());
    }

    @Test
    public void testContinuousBlockFall() {
        Game game = new Game();
        game.spawnBlocks();
        TetrisBlock mockBlock = Mockito.mock(TetrisBlock.class);

        // Configuração do mock
        when(mockBlock.isAtBottomEdge()).thenReturn(false).thenReturn(false).thenReturn(true);

        // Simula o comportamento do bloco
        game.tetris_block = mockBlock;
        Arena mockArena = Mockito.mock(Arena.class);
        game.arena = mockArena;

        // Simula as quedas do bloco
        boolean firstFall = game.continuousBlockFall(new Position(5, 10));
        boolean secondFall = game.continuousBlockFall(new Position(5, 11));
        boolean thirdFall = game.continuousBlockFall(new Position(5, 12));

        // Verificações
        Assertions.assertTrue(firstFall);
        Assertions.assertTrue(secondFall);
        Assertions.assertFalse(thirdFall);

        // Verifica se o bloco foi movido para o fundo
        verify(mockArena).moveBlocktoBackground(mockBlock);
    }

    @Test
    public void testInputMoveBlock() throws IOException {
        Game game = new Game();
        TetrisBlock mockBlock = Mockito.mock(TetrisBlock.class);
        game.tetris_block = mockBlock;

        // Simula as teclas pressionadas
        game.inputMoveBlock(new KeyStroke(KeyType.ArrowUp));
        verify(mockBlock).rotateBlock();

        game.inputMoveBlock(new KeyStroke(KeyType.ArrowDown));
        verify(mockBlock).dropBlock();

        game.inputMoveBlock(new KeyStroke(KeyType.ArrowLeft));
        verify(mockBlock).moveLeft();

        game.inputMoveBlock(new KeyStroke(KeyType.ArrowRight));
        verify(mockBlock).moveRight();
    }
}
