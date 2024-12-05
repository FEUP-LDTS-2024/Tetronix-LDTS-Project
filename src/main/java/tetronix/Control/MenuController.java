package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import tetronix.Game;
import tetronix.Model.Menu;
import tetronix.View.MenuView;
import tetronix.View.MenuView;

import java.io.IOException;


public class MenuController {
    private Menu model;
    private MenuView view;
    private boolean running = true;

    public MenuController(Menu model_, MenuView view_) {
        this.model = model_;
        this.view = view_;
    }

    public void run() throws Exception {
        while (running) {
            //para renderizar o menu
            view.visor(model.getOpcoes(), model.getSelecao());

            //le a entrada do usuario
            KeyStroke key = view.screen.readInput();
            handleInput(key);
        }
    }

    private void handleInput(KeyStroke key) throws IOException {
        if (key == null) return;

        switch (key.getKeyType()) {
            case ArrowUp -> model.voltarOpcao();
            case ArrowDown -> model.proximaOpcao();
            case Enter -> handleSelection();
            case Escape -> running = false;
            default -> {

            }
        }
    }

    private void handleSelection() throws IOException {
        int selected = model.getSelecao();
        switch (selected) {
            case 0 -> {
                System.out.println("Starting Game...");
                Game game = new Game();
                game.run();
            }
            case 1 -> {
                System.out.println("Options...");

            }
            case 2 -> {
                System.out.println("Exit Game...");
                System.exit(0);
                running = false;
            }

        }
    }

}
