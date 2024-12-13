package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tetronix.Model.Menu;

import static tetronix.Model.MenuState.PLAYING;
import static tetronix.Model.MenuState.STATISTICS;

public class InputHandlerForMenu implements InputHandler {
    private final Menu menu;

    public InputHandlerForMenu(Menu menu_) {
        this.menu = menu_;
    }

    @Override
    public void processInput(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp:

                menu.setSelectedOption((menu.getSelectedOption() > 0)
                        ? menu.getSelectedOption() - 1
                        : menu.getOptions().size() - 1);
                break;
            case ArrowDown:

                menu.setSelectedOption((menu.getSelectedOption() < menu.getOptions().size() - 1)
                        ? menu.getSelectedOption() + 1
                        : 0);
                break;
            case Enter:

                handleSelection();
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

    private void handleSelection() {
        String selectedOption = menu.getOptions().get(menu.getSelectedOption());
        switch (selectedOption) {
            case "Start Game":
                menu.setCurr_state(PLAYING);
                break;

            case "Exit":
                System.exit(0);
                break;
            default:
                System.out.println("Unknown option!");
        }
    }
}