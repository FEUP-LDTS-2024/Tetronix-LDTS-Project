package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tetronix.Model.Menu;

import static tetronix.Model.MenuState.PLAYING;
import static tetronix.Model.MenuState.STATISTICS;

public class InputHandlerForMenu implements InputHandler{
    private Menu menu;

    public InputHandlerForMenu(Menu menu_){
        this.menu = menu_;
    }


    @Override
    public void processInput(KeyStroke key){

        switch (key.getKeyType()) {
            case Enter:
                menu.setCurr_state(PLAYING);
                break;
            case Escape:
                System.exit(0);
                break;
            case Character:
                char c = key.getCharacter();
                if (c == 'n' || c == 'N') {
                    menu.setCurr_state(PLAYING);
                } else if (c == 's' || c == 'S') {
                    menu.setCurr_state(STATISTICS);
                }
                break;
        }
    }
}
