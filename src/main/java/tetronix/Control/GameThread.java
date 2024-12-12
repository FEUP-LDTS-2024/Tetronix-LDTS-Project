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
            if (game.can_level_up()) {
                System.out.println("Increasing speed...\n");
                game.setLevel(game.getLevel() + 1);
            }
            level = game.getLevel();

            try {
                // Update bombs before checking game state
                game.updateBombs(); // Ensure bombs fall continuously

                // Update game state for Tetris blocks
                if (!game.updateGameState()) {
                    game.setTetris_block(null); // Stop block manipulation after game ends
                    break;
                }

                // Render the updated state
                game.getGameView().render();

                // Sleep based on game level speed
                Thread.sleep(initial_speed - (level * speed_per_level));
            } catch (InterruptedException | IOException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }


}

