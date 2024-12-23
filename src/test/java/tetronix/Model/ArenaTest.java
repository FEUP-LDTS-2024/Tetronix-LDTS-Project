package tetronix.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArenaTest {

    private Arena arena;
    private TetrisBlock mockBlock;
    private Position mockPosition;

    @BeforeEach
    void setUp() {
        arena = new Arena(10, 20); // 10 colunas x 20 linhas
        mockBlock = mock(TetrisBlock.class);
        mockPosition = mock(Position.class);
    }

    @Test
    void testIsAtRightEdge() {
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getColumn_identifier()).thenReturn(6); // Próximo da borda direita
        when(mockBlock.getShape()).thenReturn(new int[][]{{1,0},
                                                          {1,0},
                                                          {1,1}}); // 2 células de largura

        boolean result = arena.isAtRightEdge(mockBlock);

        assertTrue(result, "O bloco deveria estar na borda direita.");
    }

    @Test
    void testIsAtLeftEdge() {
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getColumn_identifier()).thenReturn(0); // Na borda esquerda

        boolean result = arena.isAtLeftEdge(mockBlock);

        assertTrue(result, "O bloco deveria estar na borda esquerda.");
    }

    @Test
    void testIsAtBottomEdge() {
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getRow_identifier()).thenReturn(19); // Na última linha
        when(mockBlock.getShape()).thenReturn(new int[][]{{1}});

        boolean result = arena.isAtBottomEdge(mockBlock);

        assertTrue(result, "O bloco deveria estar na borda inferior.");
    }

    @Test
    void testCanMoveDown_NoCollision() {
        when(mockBlock.getShape()).thenReturn(new int[][]{{1}});
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getRow_identifier()).thenReturn(18); // Uma linha antes da borda

        boolean result = arena.canMoveDown(mockBlock);

        assertTrue(result, "O bloco deveria poder mover para baixo.");
    }

    @Test
    void testCanMoveDown_WithCollision() {
        when(mockBlock.getShape()).thenReturn(new int[][]{{1}});
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getRow_identifier()).thenReturn(18);

        arena.getBackground()[19][0] = "filled"; // Colisão na linha de baixo

        boolean result = arena.canMoveDown(mockBlock);

        assertFalse(result, "O bloco não deveria poder mover para baixo devido à colisão.");
    }

    @Test
    void testCanMoveLeft_NoCollision() {
        when(mockBlock.getShape()).thenReturn(new int[][]{{1}});
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getColumn_identifier()).thenReturn(2);

        boolean result = arena.canMoveLeft(mockBlock);

        assertTrue(result, "O bloco deveria poder mover para a esquerda.");
    }

    @Test
    void testCanMoveLeft_WithCollision() {
        when(mockBlock.getShape()).thenReturn(new int[][]{{1}});
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getColumn_identifier()).thenReturn(2);

        arena.getBackground()[0][0] = "filled"; // Colisão à esquerda

        boolean result = arena.canMoveLeft(mockBlock);

        assertFalse(result, "O bloco não deveria poder mover para a esquerda devido à colisão.");
    }

    @Test
    void testCanMoveRight_AtRightEdge() {
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getColumn_identifier()).thenReturn(6); // Última coluna
        when(mockBlock.getShape()).thenReturn(new int[][]{{1, 1}});

        boolean result = arena.canMoveRight(mockBlock);

        assertFalse(result, "Bloco na borda direita não deve poder mover para a direita.");
    }

    @Test
    void testCanMoveRight_NoCollision() {
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getRow_identifier()).thenReturn(5);
        when(mockPosition.getColumn_identifier()).thenReturn(2);
        when(mockBlock.getShape()).thenReturn(new int[][]{
                {1, 0},
                {1, 1}
        });

        // Garantir que as células à direita estão livres
        arena.getBackground()[5][4] = null;
        arena.getBackground()[6][6] = null;

        boolean result = arena.canMoveRight(mockBlock);

        assertTrue(result, "Bloco deve poder mover para a direita sem colisões.");
    }

    @Test
    void testCanMoveRight_WithCollision() {
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getRow_identifier()).thenReturn(5);
        when(mockPosition.getColumn_identifier()).thenReturn(2);
        when(mockBlock.getShape()).thenReturn(new int[][]{
                {1, 0},
                {1, 1}
        });

        // Configurar uma colisão à direita
        arena.getBackground()[5][4] = "filled";
        arena.getBackground()[6][6] = null;

        boolean result = arena.canMoveRight(mockBlock);

        assertFalse(result, "Bloco não deve poder mover para a direita devido a colisão.");
    }

    @Test
    void testClearLines() {
        // Preencher uma linha inteira
        for (int c = 0; c < 10; c++) {
            arena.getBackground()[19][c] = "filled";
        }

        int clearedLines = arena.clearLines();

        assertEquals(1, clearedLines, "Uma linha deveria ter sido limpa.");
        for (int c = 0; c < 10; c++) {
            assertNull(arena.getBackground()[19][c], "A linha limpa deveria estar vazia.");
        }
    }

    @Test
    void testCanRotate_NoCollision() {
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getRow_identifier()).thenReturn(0);
        when(mockPosition.getColumn_identifier()).thenReturn(0);
        when(mockBlock.getPossible_shapes()).thenReturn(new int[][][]{{{1},{1}}});
        when(mockBlock.getShape()).thenReturn(new int[][]{{1},{1}});

        boolean result = arena.canRotate(mockBlock, 0);
        assertTrue(result, "O bloco deveria poder rotacionar sem colisões.");
    }

    @Test
    void testTryCollectCoin() {
        // Configurar moedas e blocos
        Coins coin = mock(Coins.class);
        List<Coins> coins = new ArrayList<>();
        coins.add(coin);

        when(mockBlock.getShape()).thenReturn(new int[][]{{1}});
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getRow_identifier()).thenReturn(5);           //5 ; 4
        when(mockPosition.getColumn_identifier()).thenReturn(4);

        Position coinPosition = mock(Position.class);
        when(coin.getPosition()).thenReturn(coinPosition);
        when(coinPosition.getRow_identifier()).thenReturn(5);           //5 ; 8
        when(coinPosition.getColumn_identifier()).thenReturn(5);
        when(coin.isCollected()).thenReturn(false);

        arena.try_Collect_Coin(coins, mockBlock);

        verify(coin).collect();
    }

    @Test
    void testMoveBlocktoBackground() {
        when(mockBlock.getShape()).thenReturn(new int[][]{
                {1, 0},
                {1, 1}
        });
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getRow_identifier()).thenReturn(18); // Posição na linha 18
        when(mockPosition.getColumn_identifier()).thenReturn(4); // Posição na coluna 4
        when(mockBlock.getColor()).thenReturn("blue");

        arena.moveBlocktoBackground(mockBlock);

        // Verificar se as posições corretas foram preenchidas no background
        assertEquals("blue", arena.getBackground()[18][4], "Célula deveria estar preenchida com a cor 'blue'.");
        assertEquals("blue", arena.getBackground()[18][5], "Célula duplicada horizontalmente deveria estar preenchida.");
        assertEquals("blue", arena.getBackground()[19][4], "Célula deveria estar preenchida com a cor 'blue'.");
        assertEquals("blue", arena.getBackground()[19][5], "Célula duplicada horizontalmente deveria estar preenchida.");

        // Verificar que células não ocupadas permanecem null
        assertNull(arena.getBackground()[17][4], "Célula não ocupada deveria ser null.");
        assertNull(arena.getBackground()[17][5], "Célula não ocupada deveria ser null.");
    }

    @Test
    void testIsBlockOutBoundsAfterRotation_RightLimit() {
        when(mockBlock.getShape()).thenReturn(new int[][]{
                {1, 0},
                {1, 1}
        });
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getColumn_identifier()).thenReturn(8); // Próximo ao limite direito
        when(mockPosition.getRow_identifier()).thenReturn(15); // Dentro da altura da arena

        boolean result = arena.isBlockOutBoundsAfterRotation(mockBlock);

        assertTrue(result, "O bloco deveria estar fora dos limites à direita após a rotação.");
    }

    @Test
    void testIsBlockOutBoundsAfterRotation_BottomLimit() {
        when(mockBlock.getShape()).thenReturn(new int[][]{
                {1, 1},
                {1, 1}
        });
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getColumn_identifier()).thenReturn(2); // Longe das bordas laterais
        when(mockPosition.getRow_identifier()).thenReturn(19); // Última linha

        boolean result = arena.isBlockOutBoundsAfterRotation(mockBlock);

        assertTrue(result, "O bloco deveria estar fora dos limites inferiores após a rotação.");
    }

    @Test
    void testIsBlockOutBoundsAfterRotation_WithinBounds() {
        when(mockBlock.getShape()).thenReturn(new int[][]{
                {1, 1},
                {1, 1}
        });
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getColumn_identifier()).thenReturn(4); // Posição central
        when(mockPosition.getRow_identifier()).thenReturn(10); // Meio da arena

        boolean result = arena.isBlockOutBoundsAfterRotation(mockBlock);

        assertFalse(result, "O bloco deveria estar dentro dos limites após a rotação.");
    }

}
