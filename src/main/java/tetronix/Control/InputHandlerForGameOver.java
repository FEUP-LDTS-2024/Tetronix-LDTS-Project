package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tetronix.Model.Menu;

import static tetronix.Model.MenuState.PLAYING;
import static tetronix.Model.MenuState.STATISTICS;

public class InputHandlerForGameOver implements InputHandler {
    private final Menu menu;

    public InputHandlerForGameOver(Menu menu_) {
        this.menu = menu_;
    }

    @Override
    public void processInput(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp:
                menu.setSelectedOption((menu.getSelectedOption() > 0)
                        ? menu.getSelectedOption() - 1
                        : menu.getOptionsGameOver().size() - 1);
                break;

            case ArrowDown:
                menu.setSelectedOption((menu.getSelectedOption() < menu.getOptionsGameOver().size() - 1)
                        ? menu.getSelectedOption() + 1
                        : 0);
                break;
            case Enter:
                handleSelection();
                break;
            case Escape:
                System.exit(0);
                break;
        }
    }

    private void handleSelection() {
        String selectedOption = menu.getOptionsGameOver().get(menu.getSelectedOption());

        switch (selectedOption) {
            case "Start Game":
                menu.setCurr_state(PLAYING); // Reinicia o jogo
                break;

            case "Statistics":
                menu.setCurr_state(STATISTICS); // Mostra as estatísticas
                break;

            case "Exit":
                System.exit(0); // Sai do jogo
                break;

            default:
                System.out.println("Unknown option selected: " + selectedOption);
        }
    }
}
