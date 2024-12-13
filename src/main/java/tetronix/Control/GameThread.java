package tetronix.Control;

import tetronix.Game;
import tetronix.Model.Menu;
import tetronix.Model.MenuState;
import tetronix.Model.TetrisBlock;
import tetronix.Model.TetrisBlockFactory;
import tetronix.View.GameView;

import java.io.IOException;

import static tetronix.Model.MenuState.GAME_OVER;


public class GameThread extends Thread {
    private final Game game;

    private int speed_per_level;
    private int initial_speed;
    private int level;

    public GameThread(Game game_) {
        this.game = game_;
        initial_speed = game_.getInitial_speed();
        speed_per_level = game_.getSpeed_per_level();
    }

    @Override
    public void run() {
        while (true) {
            if(game.can_level_up()){
                System.out.println("Increasing speed...\n");
                game.setLevel(game.getLevel() + 1);
            }
            level = game.getLevel();

            try {

                if(!game.updateGameState()){
                    game.setTetris_block(null); //assim n dá para mexer mais no bloco quando jogo acaba
                    break;
                }
                Thread.sleep(initial_speed - (level * speed_per_level)); // Controla o intervalo de atualização
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

}

