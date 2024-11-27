package tetronix.Control;

import tetronix.Game;
import tetronix.Model.TetrisBlock;
import tetronix.Model.TetrisBlockFactory;
import tetronix.View.GameView;

import java.io.IOException;



public class GameThread extends Thread {
    private final Game game;
    private int level = 1;
    private int score_per_level = 1;
    private int current_score;
    private int speed_per_level = 200;
    private int pause = 500;

    public GameThread(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (true) {
            current_score = game.getScore();
            int can_up_level = current_score / score_per_level + 1;

            if(can_up_level > level){
                System.out.println("Level Up!,increasing speed...\n");
                level = can_up_level;
                pause -= speed_per_level;
            }

            try {

                if(!game.updateGameState()){
                    System.out.println("GameOver!\n");
                    game.setTetris_block(null); //assim n dá para mexer mais no bloco quando jogo acaba
                    break;
                }
                Thread.sleep(pause); // Controla o intervalo de atualização
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }
}

