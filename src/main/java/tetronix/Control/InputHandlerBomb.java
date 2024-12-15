package tetronix.Control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tetronix.Model.Bomb;
import tetronix.Model.Arena;
import tetronix.Model.Position;

import java.util.List;

public class InputHandlerBomb {

    private Arena arena; // Referência para a arena
    private List<Bomb> bombs; // Lista de bombas no jogo

    public InputHandlerBomb(Arena arena, List<Bomb> bombs) {
        this.arena = arena;
        this.bombs = bombs;
    }

    // Processa o input do usuário para mover a última bomba
    public void processInput(KeyStroke keyStroke) {
        if (bombs.isEmpty()) return;

        Bomb lastBomb = bombs.get(bombs.size() - 1); // Pega a última bomba na lista
        Position position = lastBomb.getPosition();

        // Movimentos da bomba baseados nas teclas de seta
        if (keyStroke.getKeyType() == KeyType.ArrowLeft && arena.canMoveLeft(lastBomb)) {
            position.setColumn_identifier(position.getColumn_identifier() - 1);
        } else if (keyStroke.getKeyType() == KeyType.ArrowRight && arena.canMoveRight(lastBomb)) {
            position.setColumn_identifier(position.getColumn_identifier() + 1);
        } else if (keyStroke.getKeyType() == KeyType.ArrowDown && arena.canMoveDown(lastBomb)) {
            position.setRow_identifier(position.getRow_identifier() + 1);
        }
    }
}
