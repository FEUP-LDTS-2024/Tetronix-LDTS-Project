package tetronix;

import tetronix.Control.MenuController;
import tetronix.Control.PauseController;
import tetronix.Model.Menu;
import tetronix.Model.Pause;
import tetronix.View.MenuView;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import tetronix.View.*;


public class PauseMenu {
    private final Screen screen;
    private final PauseController controller;

    public PauseMenu() throws Exception {
        this.screen = new DefaultTerminalFactory().createScreen();
        this.screen.startScreen();

        Pause model = new Pause();
        PauseView view = new PauseView(screen);
        this.controller = new PauseController(model, view);
    }

    public void show() throws Exception {
        // Executa o menu principal
        controller.run();
    }

    public void close() throws Exception {
        screen.close();
    }
}
