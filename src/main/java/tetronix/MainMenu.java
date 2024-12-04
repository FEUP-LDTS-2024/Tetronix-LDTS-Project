package tetronix;

import tetronix.Control.MenuController;
import tetronix.Model.Menu;
import tetronix.View.MenuVieww;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import tetronix.View.*;


public class MainMenu {
    private final Screen screen;
    private final MenuController controller;

    public MainMenu() throws Exception {
        // Configuração do terminal e tela
        this.screen = new DefaultTerminalFactory().createScreen();
        this.screen.startScreen();

        // Instâncias do MVC
        Menu model = new Menu();
        MenuVieww view = new MenuVieww(screen);
        this.controller = new MenuController(model, view);
    }

    public void show() throws Exception {
        // Executa o menu principal
        controller.run();
    }

    public void close() throws Exception {
        // Fecha a tela ao sair
        screen.close();
    }
}
