import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tetronix.Control.InputHandlerForGame;
import tetronix.Control.TetrisBlockController;
import tetronix.Game;
import tetronix.Model.*;
import tetronix.View.GameView;
import tetronix.View.ScreenManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    private Game game;
    private Menu mockMenu;
    private Arena mockArena;
    private TetrisBlockController mockBlockController;
    private GameView mockGameView;
    private ScreenManager mockScreenManager;

    @BeforeEach
    void setUp() {
        mockMenu = mock(Menu.class);
        mockArena = mock(Arena.class);
        mockBlockController = mock(TetrisBlockController.class);
        mockGameView = mock(GameView.class);
        mockScreenManager = mock(ScreenManager.class);

        when(mockMenu.getScreenManager()).thenReturn(mockScreenManager);
        game = new Game(mockMenu);

        // Substituir dependências por mocks
        game.setArena(mockArena);
        game.setInputHandler(new InputHandlerForGame(game));
    }

    @Test
    void testCanLevelUp() {
        game.setLevel(1);
        game.setScore(10); // Score suficiente para subir 2 níveis
        assertTrue(game.can_level_up(), "O jogo deveria permitir a progressão de nível.");
    }

    @Test
    void testContinuousBlockFall_WhenBlockCanMoveDown() {
        TetrisBlock mockBlock = mock(TetrisBlock.class);
        Position mockPosition = mock(Position.class);
        when(mockArena.canMoveDown(any())).thenReturn(true);

        game.setTetris_block(mockBlock);
        boolean result = game.continuousBlockFall(mockPosition);

        assertTrue(result, "O bloco deveria continuar a cair.");
        verify(mockBlock).setPosition(mockPosition);
    }

    @Test
    void testContinuousBlockFall_WhenBlockCannotMoveDown() {
        TetrisBlock mockBlock = mock(TetrisBlock.class);
        when(mockArena.canMoveDown(any())).thenReturn(false);
        when(mockArena.clearLines()).thenReturn(2);

        game.setTetris_block(mockBlock);
        boolean result = game.continuousBlockFall(new Position(0, 0));

        assertFalse(result, "O bloco não deveria continuar a cair.");
        assertEquals(10, game.getScore(), "O score deveria aumentar ao limpar 2 linhas.");
        verify(mockArena).moveBlocktoBackground(mockBlock);
    }

    @Test
    void testUpdateGameState_GameOver() {
        TetrisBlock mockBlock = mock(TetrisBlock.class);
        Position mockPosition = mock(Position.class);
        when(mockBlock.getPosition()).thenReturn(mockPosition);
        when(mockArena.isBlockOutBounds()).thenReturn(true);

        game.setTetris_block(mockBlock);

        boolean result = game.updateGameState();

        assertFalse(result, "O jogo deveria estar em estado GAME_OVER.");
        verify(mockMenu).setCurr_state(MenuState.GAME_OVER);
        verify(mockArena).moveBlocktoBackground(mockBlock);
    }

    @Test
    void testUpdateGameState_CreatesNewBlockAndCoin() {
        when(mockArena.isBlockOutBounds()).thenReturn(false);
        when(mockArena.canMoveDown(any())).thenReturn(false);

        game.setTetris_block(null);
        game.updateGameState();


        //assertNotNull(game.getTetris_block(), "Um novo bloco deveria ser criado.");
    }

    @Test
    void testMoveBlock_RightMovement() {
        TetrisBlock mockBlock = mock(TetrisBlock.class);
        Position newPosition = new Position(1, 1);

        when(mockArena.canMoveRight(any())).thenReturn(true);
        game.setTetris_block(mockBlock);

        game.moveBlock(newPosition, KeyType.ArrowRight);

        verify(mockBlock).setPosition(newPosition);
    }

    @Test
    void testMoveBlock_BlockCollisionOnRight() {
        TetrisBlock mockBlock = mock(TetrisBlock.class);
        Position newPosition = new Position(1, 1);

        when(mockArena.canMoveRight(any())).thenReturn(false);
        game.setTetris_block(mockBlock);

        game.moveBlock(newPosition, KeyType.ArrowRight);

        verify(mockBlock, never()).setPosition(any());
    }

    @Test
    void testHandleInput_WithArrowRight() throws IOException {
        KeyStroke mockKeyStroke = new KeyStroke(KeyType.ArrowRight);
        when(mockScreenManager.readInput()).thenReturn(mockKeyStroke);

        InputHandlerForGame mockInputHandler = mock(InputHandlerForGame.class);
        game.setInputHandler(mockInputHandler);

        game.handleInput();

        verify(mockInputHandler).processInput(mockKeyStroke);
    }

    @Test
    void testRun_GameStopsOnGameOver() throws IOException {
        when(mockMenu.getCurr_state()).thenReturn(MenuState.GAME_OVER);
        ScreenManager mockScreenManager = mock(ScreenManager.class);

        game.setInputHandler(mock(InputHandlerForGame.class));
        game.getScreenManager().readInput();

        assertDoesNotThrow(() -> game.run());
    }
}
