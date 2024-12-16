package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetronix.Game;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class InputHandlerForGameTest {

    private Game mockGame;
    private TetrisBlockController mockTetrisBlockController;
    private InputHandlerForGame inputHandlerForGame;

    @BeforeEach
    void setUp() {
        mockGame = mock(Game.class); // Mock do Game
        mockTetrisBlockController = mock(TetrisBlockController.class); // Mock do TetrisBlockController

        inputHandlerForGame = new InputHandlerForGame(mockGame); // Instancia o InputHandlerForGame
    }

}
