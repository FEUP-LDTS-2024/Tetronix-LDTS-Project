package tetronix.Control;

import tetronix.Game;
import tetronix.Model.TetrisBlock;
import tetronix.Model.TetrisBlockFactory;
import tetronix.View.GameView;

import java.io.IOException;



public class GameThread extends Thread {
    private final Game game;

    public GameThread(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (true) {
            try {
                game.updateGameState(); // Atualiza o estado do jogo
                Thread.sleep(200); // Controla o intervalo de atualização
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

