import com.googlecode.lanterna.input.KeyStroke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tetronix.*;

import static org.mockito.Mockito.*;

import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private TetrisBlock mockBlock;
    private Arena mockArena;

    @BeforeEach
    void setUp() {
        // Mockando Arena e TetrisBlock
        mockBlock = mock(TetrisBlock.class);
        mockArena = mock(Arena.class);

        // Inicializando a classe Game com as dependências reais/mocks
        game = new Game();
        game.setTetris_block(mockBlock);
        game.setArena(mockArena);
    }

    @Test
    void testContinuousBlockFall_WhenBlockCanMoveDown() {
        // Arrange
        Position nextPosition = new Position(5, 10); // Posição hipotética
        when(mockBlock.canMoveDown(mockArena)).thenReturn(true);

        // Act
        boolean result = game.continuousBlockFall(nextPosition);

        // Assert
        assertTrue(result, "The block should continue falling");
        verify(mockBlock, times(1)).setPosition(nextPosition); // Verifica se moveu o bloco
        verify(mockBlock, times(1)).canMoveDown(mockArena);   // Verifica se checou se o bloco pode descer
        verify(mockArena, never()).moveBlocktoBackground(any()); // Certifica-se de que o bloco NÃO foi movido para o background
    }

    @Test
    void testContinuousBlockFall_WhenBlockCannotMoveDown() {
        // Arrange
        Position nextPosition = new Position(5, 10); // Posição hipotética
        when(mockBlock.canMoveDown(mockArena)).thenReturn(false);

        // Act
        boolean result = game.continuousBlockFall(nextPosition);

        // Assert
        assertFalse(result, "The block should stop falling");
        verify(mockBlock, never()).setPosition(any()); // Certifica-se de que o bloco NÃO mudou de posição
        verify(mockArena, times(1)).moveBlocktoBackground(mockBlock); // Verifica se o bloco foi adicionado ao background
    }

    @Test
    void testMoveBlock_RightMovement() {
        // Arrange
        Position nextPosition = new Position(5, 10);
        when(mockBlock.isAtRightEdge()).thenReturn(false); // Certifique-se de que o bloco não está na borda direita

        // Act
        game.moveBlock(nextPosition, KeyType.ArrowRight);

        // Assert
        verify(mockBlock, times(1)).setPosition(nextPosition);
    }

    @Test
    void testMoveBlock_LeftEdgeDoesNotMove() {
        // Arrange
        Position nextPosition = new Position(5, 10);
        when(mockBlock.isAtLeftEdge()).thenReturn(true); // Simula que o bloco está na borda esquerda

        // Act
        game.moveBlock(nextPosition, KeyType.ArrowLeft);

        // Assert
        verify(mockBlock, never()).setPosition(nextPosition); // O bloco não deve se mover
    }

    @Test
    void testDropBlock() {
        // Arrange
        when(mockBlock.canMoveDown(mockArena)).thenReturn(true, true, false); // Pode descer 2 vezes, depois não

        // Act
        game.dropBlock();

        // Assert
        verify(mockBlock, times(2)).moveDown(); // Deve descer 2 vezes
        verify(mockBlock, times(2)).setPosition(any()); // Deve ajustar a posição 2 vezes
        verify(mockArena, times(1)).moveBlocktoBackground(mockBlock); // Deve adicionar o bloco ao background ao final
    }

    @Test
    void testRenderImage() throws IOException {
        // Act
        game.renderImage();

        // Assert
        verify(mockArena, times(1)).draw(any()); // Arena deve ser desenhada
        verify(mockBlock, times(1)).draw(any()); // Bloco deve ser desenhado
    }

}