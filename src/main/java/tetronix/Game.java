package tetronix;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tetronix.Control.*;
import tetronix.Model.*;
import tetronix.Model.Bomb;
import tetronix.Model.Arena;
import tetronix.Control.InputHandlerBomb;

import tetronix.View.*;
import tetronix.Model.Bomb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.googlecode.lanterna.input.KeyType.*;
import static tetronix.Model.MenuState.*;

public class Game {
    // Configurações básicas do jogo
    private int rows = 20;
    private int columns = 20;
    private int score = 0;
    private int level = 1;
    private int score_per_level = 5;
    private int speed_per_level = 50;
    private int initial_speed = 600;
    private boolean isBombFalling = false;
    private long lastBombFallTime = 0;
    private int bombFallSpeed = 600;

    // Componentes principais
    private Menu menu;
    private Arena arena;
    private TetrisBlock tetris_block;
    private List<Coins> coins = new ArrayList<>();
    private List<Bomb> bombs = new ArrayList<>();
    private int count_to_create_coins = 0;

    // Gerenciadores
    private InputHandler inputHandler;
    private TetrisBlockController tetrisBlockController;
    private ScreenManager screenManager;
    private GameView gameView;

    // Construtor
    public Game(Menu menu_) {
        this.menu = menu_;
        this.screenManager = menu_.getScreenManager();
        this.inputHandler = new InputHandlerForGame(this);
        this.arena = new Arena(columns, rows, coins);
        this.tetris_block = TetrisBlockFactory.createBlock(columns, rows);
        this.gameView = new GameView(this);
        this.tetrisBlockController = new TetrisBlockController(this);
    }

    // Métodos auxiliares para pontuação e nível
    public int get_Additional_Points() {
        int points = 0;
        for (Coins coin_ : coins) {
            if (coin_.isCollected()) {
                points += coin_.getValue();
            }
        }
        return points;
    }

    public boolean can_level_up() {
        int can_up_level = score / score_per_level + 1;
        return can_up_level > level;
    }

    // Bombas a cairem do topo da arena
    public void spawnBomb() {
        Bomb bomb = BombFactory.createBomb(columns, rows);
        bomb.getPosition().setRow_identifier(0); // Start at the top
        bombs.add(bomb);
        System.out.println("Bomb spawned at: Row " + bomb.getPosition().getRow_identifier() + ", Column " + bomb.getPosition().getColumn_identifier());
    }


    // Getters e Setters
    public List<Coins> getCoins() {
        return coins;
    }

    public List<Bomb> getBomb() {
        return bombs;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getInitial_speed() {
        return initial_speed;
    }

    public int getScore_per_level() {
        return score_per_level;
    }

    public int getSpeed_per_level() {
        return speed_per_level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public TetrisBlockController getTetrisBlockController() {
        return tetrisBlockController;
    }

    public TetrisBlock getTetris_block() {
        return tetris_block;
    }

    public void setTetris_block(TetrisBlock tetrisblock_) {
        this.tetris_block = tetrisblock_;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }


    // Controle do jogo
    public boolean continuousBlockFall(Position position) {
        if (arena.canMoveDown(tetris_block)) {
            tetris_block.setPosition(position);
            return true;
        } else {
            arena.moveBlocktoBackground(tetris_block);
            score += arena.clearLines() * 5;
            return false;
        }
    }

    public boolean updateGameState() {
        if (isBombFalling) {
            // Update bomb falling logic
            updateBombs();
            if (bombs.isEmpty()) {
                isBombFalling = false; // No more bombs are falling
            }
            return true;
        }

        if (arena.isBlockOutBounds()) {
            System.out.println("Game Stopped!: Row: " + tetris_block.getPosition().getRow_identifier()
                    + " ------ " + "Column: " + tetris_block.getPosition().getColumn_identifier());
            arena.moveBlocktoBackground(tetris_block);
            menu.setCurr_state(GAME_OVER);
            return false;
        }


        if (tetris_block == null || !continuousBlockFall(tetrisBlockController.moveDown())) {
            tetris_block = TetrisBlockFactory.createBlock(columns, rows);
            count_to_create_coins++;
            if (count_to_create_coins % 3 == 0) {
                System.out.println("Coin created!\n");
                coins.add(CoinsFactory.createCoin(arena));
            }
            if (new Random().nextInt(10) == 0) { // 10% chance to spawn a bomb
                spawnBomb();
                isBombFalling = true; // Set flag to prioritize bomb falling
            } else {
                tetris_block = TetrisBlockFactory.createBlock(columns, rows);
            }

        }

        try {
            gameView.render();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public void moveBlock(Position position, KeyType key) {
        if (key == ArrowRight && !arena.canMoveRight(tetris_block)) return;
        if (key == ArrowLeft && !arena.canMoveLeft(tetris_block)) return;
        tetris_block.setPosition(position);
    }


    // Entrada do usuário
    public void handleInput() throws IOException {
        KeyStroke key = screenManager.readInput();
        inputHandler.processInput(key);
    }

    //Bombs fall individually
    public void updateBombs() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBombFallTime < bombFallSpeed) {
            return; // Skip if not enough time has passed
        }
        lastBombFallTime = currentTime;

        List<Bomb> bombsToRemove = new ArrayList<>();
        for (Bomb bomb : bombs) {
            Position currentPosition = bomb.getPosition();

            if (arena.canMoveDown(bomb)) {
                currentPosition.setRow_identifier(currentPosition.getRow_identifier() + 1);
            } else {
                bomb.explode(arena.getBackground());
                bombsToRemove.add(bomb);

                // Check for any cleared lines after explosion
                int linesCleared = arena.clearLines();
                if (linesCleared > 0) {
                    score += linesCleared * 5; // Increment score for cleared lines
                }
            }
        }
        bombs.removeAll(bombsToRemove);
    }

    // Execução do jogo
    public void run() throws IOException {
        GameThread gameThread = new GameThread(this);
        gameThread.start();

        while (true) {
            handleInput();
            gameView.render();
            if (menu.getCurr_state() == GAME_OVER) {
                return;
            }
        }
    }


}
