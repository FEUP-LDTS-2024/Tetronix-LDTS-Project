package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetronix.Model.Menu;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class InputHandlerForMenuTest {
    private InputHandlerForMenu inputHandler;
    private Menu mockMenu;

    @BeforeEach
    void setUp() {
        mockMenu = mock(Menu.class); // Mock do Menu
        inputHandler = new InputHandlerForMenu(mockMenu); // Instancia o InputHandlerForMenu
    }

    @Test
    void testProcessInput_ArrowUp() {
        when(mockMenu.getSelectedOption()).thenReturn(1); // Opção atual é 1
        when(mockMenu.getOptions()).thenReturn(java.util.List.of("Start Game", "Statistics", "Exit"));

        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowUp);

        inputHandler.processInput(keyStroke);

        verify(mockMenu).setSelectedOption(0); // Verifica se a opção foi movida para 0
    }

    @Test
    void testProcessInput_ArrowDown() {
        when(mockMenu.getSelectedOption()).thenReturn(1); // Opção atual é 1
        when(mockMenu.getOptions()).thenReturn(java.util.List.of("Start Game", "Statistics", "Exit"));

        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowDown);

        inputHandler.processInput(keyStroke);

        verify(mockMenu).setSelectedOption(2); // Verifica se a opção foi movida para 2
    }

    @Test
    void testProcessInput_Enter_StartGame() {
        when(mockMenu.getSelectedOption()).thenReturn(0); // "Start Game" é a primeira opção
        when(mockMenu.getOptions()).thenReturn(java.util.List.of("Start Game", "Statistics", "Exit"));

        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Enter);

        inputHandler.processInput(keyStroke);

        verify(mockMenu).setCurr_state(tetronix.Model.MenuState.PLAYING); // Estado definido como PLAYING
    }

    @Test
    void testProcessInput_Enter_Statistics() {
        when(mockMenu.getSelectedOption()).thenReturn(1); // "Statistics" é a segunda opção
        when(mockMenu.getOptions()).thenReturn(java.util.List.of("Start Game", "Statistics", "Exit"));

        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Enter);

        inputHandler.processInput(keyStroke);

        verify(mockMenu).setCurr_state(tetronix.Model.MenuState.STATISTICS); // Estado definido como STATISTICS
    }


    @Test
    void testProcessInput_Character_N() {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke.getCharacter()).thenReturn('n');

        inputHandler.processInput(keyStroke);

        verify(mockMenu).setCurr_state(tetronix.Model.MenuState.PLAYING); // Estado definido como PLAYING
    }

    @Test
    void testProcessInput_Character_S() {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke.getCharacter()).thenReturn('s');

        inputHandler.processInput(keyStroke);

        verify(mockMenu).setCurr_state(tetronix.Model.MenuState.STATISTICS); // Estado definido como STATISTICS
    }

}
