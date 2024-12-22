package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetronix.Game;
import tetronix.Model.TetrisBlock;
import tetronix.Model.Arena;
import tetronix.Control.TetrisBlockController;

import static org.mockito.Mockito.*;

public class InputHandlerForGameTest {
    private InputHandlerForGame inputHandler;
    private Game mockGame;
    private TetrisBlockController mockTetrisBlockController;
    private TetrisBlock mockTetrisBlock;

    @BeforeEach
    void setUp() {
        mockGame = mock(Game.class);
        mockTetrisBlockController = mock(TetrisBlockController.class);
        mockTetrisBlock = mock(TetrisBlock.class);

        // Configura o mock para retornar o TetrisBlockController correto quando solicitado
        when(mockGame.getTetrisBlockController()).thenReturn(mockTetrisBlockController);
        when(mockGame.getTetris_block()).thenReturn(mockTetrisBlock);

        inputHandler = new InputHandlerForGame(mockGame); // Instancia o InputHandlerForGame com o mockGame
    }

    @Test
    void testProcessInput_ArrowUp() {
        // Cria um mock para o KeyStroke de ArrowUp
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowUp);

        // Chama o método processInput
        inputHandler.processInput(keyStroke);

        // Verifica se o método de rotação do TetrisBlockController foi chamado
        verify(mockTetrisBlockController, times(1)).rotateBlock();
    }

    @Test
    void testProcessInput_ArrowDown() {
        // Cria um mock para o KeyStroke de ArrowDown
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowDown);

        // Chama o método processInput
        inputHandler.processInput(keyStroke);

        // Verifica se o método de movimento para baixo foi chamado
        verify(mockTetrisBlockController, times(1)).moveDown();
    }

    @Test
    void testProcessInput_ArrowLeft() {
        // Cria um mock para o KeyStroke de ArrowLeft
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowLeft);

        // Chama o método processInput
        inputHandler.processInput(keyStroke);

        // Verifica se o método de movimento para a esquerda foi chamado
        verify(mockTetrisBlockController, times(1)).moveLeft();
    }

    @Test
    void testProcessInput_ArrowRight() {
        // Cria um mock para o KeyStroke de ArrowRight
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowRight);

        // Chama o método processInput
        inputHandler.processInput(keyStroke);

        // Verifica se o método de movimento para a direita foi chamado
        verify(mockTetrisBlockController, times(1)).moveRight();
    }

}
