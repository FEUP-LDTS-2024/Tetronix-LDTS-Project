package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tetronix.Model.Menu;

import static tetronix.Model.MenuState.PLAYING;

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
            default:
                break;
        }
    }
}
