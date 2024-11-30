import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetronix.*;

import static org.mockito.Mockito.*;

import com.googlecode.lanterna.input.KeyType;
import tetronix.control.TetrisBlockController;
import tetronix.model.Arena;
import tetronix.model.Position;
import tetronix.model.TetrisBlock;
import tetronix.view.ArenaView;
import tetronix.view.GameView;
import tetronix.view.TetrisBlockView;

import javax.swing.text.html.BlockView;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private GameView mockGameView;
    private TetrisBlock mockBlock;
    private TetrisBlockController mockBlockController;
    private Arena mockArena;
    private ArenaView mockArenaView;
    private TetrisBlockView mockTetrisBlockView;

    @BeforeEach
    void setUp() {

        mockBlock = mock(TetrisBlock.class);
        mockTetrisBlockView = mock(TetrisBlockView.class);
        mockArena = mock(Arena.class);
        mockArenaView = mock(ArenaView.class);
        mockGameView = mock(GameView.class);
        mockBlockController = mock(TetrisBlockController.class);

        game = new Game(mockArena, mockBlockController);
        game.setTetris_block(mockBlock);
        game.setArena(mockArena);
    }

    @Test
    void testContinuousBlockFall_WhenBlockCanMoveDown() {

        Position nextPosition = new Position(5, 10); // Posição hipotética
        when(mockArena.canMoveDown(mockBlock)).thenReturn(true);

        //canMoveDown is in Arena and not TetrisBlock!!!!!!!!!!!!!!!!!!!!!!!


        boolean result = game.continuousBlockFall(nextPosition);


        assertTrue(result, "The block should continue falling");
        verify(mockBlock, times(1)).setPosition(nextPosition); // Verifica se moveu o bloco
        verify(mockArena, times(1)).canMoveDown(mockBlock);   // Verifica se checou se o bloco pode descer
        verify(mockArena, never()).moveBlocktoBackground(any()); // Certifica-se de que o bloco NÃO foi movido para o background
    }

    @Test
    void testContinuousBlockFall_WhenBlockCannotMoveDown() {

        Position nextPosition = new Position(5, 10); // Posição hipotética
        when(mockArena.canMoveDown(mockBlock)).thenReturn(true);


        boolean result = game.continuousBlockFall(nextPosition);


        assertFalse(result, "The block should stop falling");
        verify(mockBlock, never()).setPosition(any()); // Certifica-se de que o bloco NÃO mudou de posição
        verify(mockArena, times(1)).moveBlocktoBackground(mockBlock); // Verifica se o bloco foi adicionado ao background
    }

    @Test
    void testMoveBlock_RightMovement() {

        Position nextPosition = new Position(5, 7);
        when(mockArena.canMoveRight(mockBlock)).thenReturn(false); // Certifica que o bloco não está na borda direita


        game.moveBlock(nextPosition, KeyType.ArrowRight);


        verify(mockBlock, times(0)).setPosition(nextPosition);
    }

    @Test
    void testMoveBlock_LeftEdgeDoesNotMove() {

        Position nextPosition = new Position(10, 5);
        when(mockArena.canMoveLeft(mockBlock)).thenReturn(false); // Simula que o bloco está na borda esquerda


        game.moveBlock(nextPosition, KeyType.ArrowLeft);


        verify(mockBlock, never()).setPosition(nextPosition); // O bloco não deve se mover
    }

    @Test
    void testDropBlock() {

        when(mockArena.canMoveDown(mockBlock)).thenReturn(true, true, false); // Pode descer 2 vezes, depois não


        game.dropBlock();


        verify(mockArena, times(2)).canMoveDown(mockBlock); // Deve descer 2 vezes
        verify(mockBlock, times(2)).setPosition(any()); // Deve ajustar a posição 2 vezes
        verify(mockArena, times(1)).moveBlocktoBackground(mockBlock); // Deve adicionar o bloco ao background ao final
        verify(mockBlockController, times(1)).dropBlock(mockArena);

    }

    @Test
    void testRenderImage() throws IOException {

        mockGameView.render();


        verify(mockArenaView, times(1)).draw(); // Arena deve ser desenhada
        verify(mockTetrisBlockView, times(1)).draw(); // Bloco deve ser desenhado
    }


}