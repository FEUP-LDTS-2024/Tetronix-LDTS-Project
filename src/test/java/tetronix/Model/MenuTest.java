package tetronix.Model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tetronix.Control.InputHandler;
import tetronix.Control.InputHandlerForGameOver;
import tetronix.View.ElementViewer;
import tetronix.View.ScreenManager;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuTest {

    private Menu menu;
    private ScreenManager mockScreenManager;
    private ElementViewer<Menu> mockMenuView;
    private ElementViewer<Menu> mockMenuGameOverView;
    private ElementViewer<Menu> mockMenuStatisticsView;
    private InputHandler mockMenuController;
    private InputHandlerForGameOver mockMenuControllerGameOver;

    @BeforeEach
    void setUp() throws IOException {
        // Mock dos componentes
        mockScreenManager = mock(ScreenManager.class);
        mockMenuView = mock(ElementViewer.class);
        mockMenuGameOverView = mock(ElementViewer.class);
        mockMenuStatisticsView = mock(ElementViewer.class);
        mockMenuController = mock(InputHandler.class);
        mockMenuControllerGameOver = mock(InputHandlerForGameOver.class);

        // Criação do menu com dependências injetadas
        menu = new Menu();
        menu.setScreenManager(mockScreenManager);
        menu.setMenuView(mockMenuView);
        menu.setMenuGame_OverView(mockMenuGameOverView);
        menu.setMenuStatisticsView(mockMenuStatisticsView);
        menu.setMenuController(mockMenuController);
        menu.setMenuControllerGameOver(mockMenuControllerGameOver);
    }

    @Test
    void testInitialMenuState() {
        assertEquals(MenuState.INITIAL_MENU, menu.getCurr_state(), "O estado inicial do menu deve ser INITIAL_MENU.");
        assertEquals(0, menu.getSelectedOption(), "A opção selecionada inicialmente deve ser 0.");
    }

    @Test
    void testRunInitialMenu() throws IOException {
        menu.setCurr_state(MenuState.INITIAL_MENU);

        // Criar um mock válido de KeyStroke
        KeyStroke mockKey = mock(KeyStroke.class);
        when(mockScreenManager.readInput()).thenReturn(mockKey);
        when(mockKey.getKeyType()).thenReturn(KeyType.ArrowUp);

        // Configurar o controlador para parar o loop
        doAnswer(invocation -> {
            menu.setIsRunning(false); // Parar o loop
            return null;
        }).when(mockMenuController).processInput(mockKey);

        // Executar o método
        menu.run();

        // Verificar se os métodos esperados foram chamados
        verify(mockMenuView, atLeastOnce()).draw();
        verify(mockScreenManager, atLeastOnce()).refresh();
        verify(mockMenuController, atLeastOnce()).processInput(mockKey);
    }




    @Test
    void testRunGameOverMenu() throws IOException {
        menu.setCurr_state(MenuState.GAME_OVER);

        // Simular entrada do usuário que para o loop
        KeyStroke mockKey = mock(KeyStroke.class);
        when(mockScreenManager.readInput()).thenReturn(mockKey);
        when(mockKey.getKeyType()).thenReturn(KeyType.ArrowDown);

        // Adicionar lógica para interromper o loop
        doAnswer(invocation -> {menu.setIsRunning(false); // Parar o loop
            return null;
        }).when(mockMenuControllerGameOver).processInput(mockKey);

        // Executar o método
        menu.run();

        // Verificar se os métodos esperados foram chamados
        verify(mockMenuGameOverView, atLeastOnce()).draw();
        verify(mockScreenManager, atLeastOnce()).refresh();
        verify(mockMenuControllerGameOver, atLeastOnce()).processInput(mockKey);
    }



    @Test
    void testHandleInputInitialMenu() throws IOException {
        KeyStroke mockKey = mock(KeyStroke.class);
        when(mockScreenManager.readInput()).thenReturn(mockKey);

        menu.setCurr_state(MenuState.INITIAL_MENU);
        menu.handleInput();

        // Verificar se o controlador do menu processou a entrada
        verify(mockMenuController, times(1)).processInput(mockKey);
        verify(mockMenuControllerGameOver, never()).processInput(any(KeyStroke.class));
    }

    @Test
    void testHandleInputGameOverMenu() throws IOException {
        KeyStroke mockKey = mock(KeyStroke.class);
        when(mockScreenManager.readInput()).thenReturn(mockKey);

        menu.setCurr_state(MenuState.GAME_OVER);
        menu.handleInput();

        // Verificar se o controlador do menu Game Over processou a entrada
        verify(mockMenuControllerGameOver, times(1)).processInput(mockKey);
        verify(mockMenuController, never()).processInput(any(KeyStroke.class));
    }

    @Test
    void testTrackScores() {
        menu.getTrack_Scores().add(100);
        menu.getTrack_Scores().add(200);

        assertEquals(2, menu.getTrack_Scores().size(), "A lista de scores deve conter 2 elementos.");
        assertTrue(menu.getTrack_Scores().contains(100), "A lista de scores deve conter o valor 100.");
        assertTrue(menu.getTrack_Scores().contains(200), "A lista de scores deve conter o valor 200.");
    }
}
