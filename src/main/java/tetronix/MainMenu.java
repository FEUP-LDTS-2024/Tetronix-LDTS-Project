package tetronix;

import tetronix.Control.MenuController;
import tetronix.Model.Menu;
import tetronix.View.MenuView;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import tetronix.View.*;


public class MainMenu {
    private final Screen screen;
    private final MenuController controller;

    public MainMenu() throws Exception {
        this.screen = new DefaultTerminalFactory().createScreen();
        this.screen.startScreen();

        Menu model = new Menu();
        MenuView view = new MenuView(screen);
        this.controller = new MenuController(model, view);
    }

    public void show() throws Exception {
        // Executa o menu principal
        controller.run();
    }

    public void close() throws Exception {
        screen.close();
    }
}
