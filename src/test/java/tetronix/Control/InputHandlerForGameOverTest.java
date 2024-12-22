package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetronix.Model.Menu;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class InputHandlerForGameOverTest {
    private InputHandlerForGameOver inputHandler;
    private Menu mockMenu;

    @BeforeEach
    void setUp() {
        mockMenu = mock(Menu.class); // Mock do Menu
        inputHandler = new InputHandlerForGameOver(mockMenu); // Instancia o InputHandlerForGameOver
    }

    @Test
    void testProcessInput_ArrowUp() {
        // Simula a situação em que a opção selecionada é 1 (por exemplo, "Statistics")
        when(mockMenu.getSelectedOption()).thenReturn(1);
        when(mockMenu.getOptionsGameOver()).thenReturn(java.util.List.of("Start Game", "Statistics", "Exit"));

        // Cria um mock para o KeyStroke de ArrowUp
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowUp);

        // Chama o método processInput
        inputHandler.processInput(keyStroke);

        // Verifica se a opção selecionada foi movida para cima (0 -> "Start Game")
        verify(mockMenu).setSelectedOption(0);
    }

    @Test
    void testProcessInput_ArrowDown() {
        // Simula a situação em que a opção selecionada é 1 (por exemplo, "Statistics")
        when(mockMenu.getSelectedOption()).thenReturn(1);
        when(mockMenu.getOptionsGameOver()).thenReturn(java.util.List.of("Start Game", "Statistics", "Exit"));

        // Cria um mock para o KeyStroke de ArrowDown
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowDown);

        // Chama o método processInput
        inputHandler.processInput(keyStroke);

        // Verifica se a opção selecionada foi movida para baixo (2 -> "Exit")
        verify(mockMenu).setSelectedOption(2);
    }

    @Test
    void testProcessInput_Enter_StartGame() {
        // Simula a situação em que a opção selecionada é "Start Game"
        when(mockMenu.getSelectedOption()).thenReturn(0); // "Start Game" é a primeira opção
        when(mockMenu.getOptionsGameOver()).thenReturn(java.util.List.of("Start Game", "Statistics", "Exit"));

        // Cria um mock para o KeyStroke de Enter
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Enter);

        // Chama o método processInput
        inputHandler.processInput(keyStroke);

        // Verifica se o estado do menu foi alterado para "PLAYING"
        verify(mockMenu).setCurr_state(tetronix.Model.MenuState.PLAYING);
    }

    @Test
    void testProcessInput_Enter_Statistics() {
        // Simula a situação em que a opção selecionada é "Statistics"
        when(mockMenu.getSelectedOption()).thenReturn(1); // "Statistics" é a segunda opção
        when(mockMenu.getOptionsGameOver()).thenReturn(java.util.List.of("Start Game", "Statistics", "Exit"));

        // Cria um mock para o KeyStroke de Enter
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Enter);

        // Chama o método processInput
        inputHandler.processInput(keyStroke);

        // Verifica se o estado do menu foi alterado para "STATISTICS"
        verify(mockMenu).setCurr_state(tetronix.Model.MenuState.STATISTICS);
    }

   /* @Test
    void testProcessInput_Enter_Exit() {
        // Simula a situação em que a opção selecionada é "Exit"
        when(mockMenu.getSelectedOption()).thenReturn(2); // "Exit" é a terceira opção
        when(mockMenu.getOptionsGameOver()).thenReturn(java.util.List.of("Start Game", "Statistics", "Exit"));

        // Cria um mock para o KeyStroke de Enter
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Enter);

        // Chama o método processInput
        inputHandler.processInput(keyStroke);

        // Verifica se o System.exit() foi chamado
        // Isto pode ser feito por meio de uma captura de exceção ou usando uma abordagem de mocking para o System.exit()
        try {
            inputHandler.processInput(keyStroke);
        } catch (SecurityException e) {
            // Captura a chamada a System.exit()
            assertTrue(true); // Se o código caiu aqui, o comportamento é esperado
        }
    }

    @Test
    void testProcessInput_Escape() {
        // Cria um mock para o KeyStroke de Escape
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Escape);

        // Chama o método processInput
        inputHandler.processInput(keyStroke);

        // Verifica se o System.exit() foi chamado
        try {
            inputHandler.processInput(keyStroke);
        } catch (SecurityException e) {
            // Captura a chamada a System.exit()
            assertTrue(true); // Se o código caiu aqui, o comportamento é esperado
        }
    }*/
}
