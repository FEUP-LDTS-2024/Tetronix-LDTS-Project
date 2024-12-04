package tetronix;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws Exception {
        MainMenu mainMenu = new MainMenu();
        Game game = new Game();
        mainMenu.show();
        game.run();
    }
}

