package tetronix.Model;

import com.googlecode.lanterna.input.KeyStroke;
import tetronix.Control.InputHandlerForMenu;
import tetronix.Control.InputHandler;
import tetronix.Game;
import tetronix.View.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static tetronix.Model.MenuState.*;

public class Menu {
    private ScreenManager screenManager;
    private Game game;
    private MenuState curr_state;
    private ElementViewer<Menu> menuView;
    private ElementViewer<Menu> menuGame_OverView;
    private ElementViewer<Menu> menuStatisticsView;
    private InputHandler menuController;

    private final List<String> options = List.of("Start Game", "Exit");
    private int selectedOption = 0;

    List<Integer> Track_Scores = new ArrayList<>();
    private int rows = 20;
    private int columns = 40;

    public Menu() throws IOException {
        screenManager = new ScreenManager(rows, columns);
        menuView = new MenuView(this);
        menuGame_OverView = new MenuGame_OverView(screenManager);
        menuStatisticsView = new MenuStatisticsView(this);
        menuController = new InputHandlerForMenu(this);
        curr_state = INITIAL_MENU;
    }

    public List<Integer> getTrack_Scores() {
        return Track_Scores;
    }

    public void setCurr_state(MenuState curr_state_) {
        this.curr_state = curr_state_;
    }

    public MenuState getCurr_state() {
        return curr_state;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public void run() throws IOException {
        while (true) {
            switch (curr_state) {
                case INITIAL_MENU:
                    menuView.draw();
                    screenManager.refresh();
                    handleInput();
                    screenManager.clear();
                    break;
                case PLAYING:
                    game = new Game(this);
                    game.run();
                    Track_Scores.add(game.getScore());
                    break;
                case GAME_OVER:
                    menuGame_OverView.draw();
                    screenManager.refresh();
                    handleInput();
                    break;
                case STATISTICS:
                    screenManager.clear();
                    menuStatisticsView.draw();
                    screenManager.refresh();
                    handleInput();
                    break;
            }
        }
    }

    public void handleInput() throws IOException {
        KeyStroke key = screenManager.readInput();
        menuController.processInput(key);
    }
}
