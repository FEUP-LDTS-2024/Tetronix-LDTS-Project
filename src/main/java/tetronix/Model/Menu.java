package tetronix.Model;

import com.googlecode.lanterna.input.KeyStroke;
import tetronix.Control.InputHandlerForMenu;
import tetronix.Control.InputHandler;
import tetronix.Game;
import tetronix.View.MenuView;
import tetronix.View.ScreenManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static tetronix.Model.MenuState.*;

public class Menu {
    private ScreenManager screenManager; //mais tarde vai passar como argumento para o game
    private Game game;
    private MenuState curr_state;
    private MenuView menuView;
    private InputHandler menuController;
    List<Integer> Track_Scores = new ArrayList<>();


    public Menu() throws IOException {
        screenManager = new ScreenManager();
        menuView = new MenuView(this);
        menuController = new InputHandlerForMenu(this);
        //game = new Game(this); //mais tarde passar screenManager
        curr_state = INITIAL_MENU;
    }

    public void setCurr_state(MenuState curr_state_) {this.curr_state = curr_state_;}

    public MenuState getCurr_state() {return curr_state;}

    public ScreenManager getScreenManager() {return screenManager;}

    public void run() throws IOException {
        while (true) {

            switch (curr_state) {
                case INITIAL_MENU:
                    menuView.draw();
                    screenManager.refresh();
                    handleInput();
                    break;

                case PLAYING:
                    game = new Game(this);
                    game.run(); // Atualiza o jogo
                    Track_Scores.add(game.getScore());

                    for(int i = 0; i < Track_Scores.size(); i++){
                        System.out.println("Score of Game " + i + ": " + Track_Scores.get(i));
                    }
                    //handleInput(); ->para mudar valor de curr_state
                    break;
                case STATISTICS:
                    //print Game i : score
                    //print Game i + 1 : score
                    //      ...

                    break;

                case GAME_OVER:
                    //fica?
                    break;
            }
        }
    }



    public void handleInput() throws IOException {
        KeyStroke key = screenManager.readInput();
        menuController.processInput(key);
    }
}



