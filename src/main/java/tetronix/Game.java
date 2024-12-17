package tetronix;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tetronix.Control.GameThread;
import tetronix.Control.InputHandler;
import tetronix.Control.InputHandlerForGame;
import tetronix.Control.TetrisBlockController;
import tetronix.Model.*;
import tetronix.View.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.googlecode.lanterna.input.KeyType.*;
import static tetronix.Model.MenuState.GAME_OVER;
import static tetronix.Model.MenuState.INITIAL_MENU;

public class Game {
    private int rows = 20;
    private int columns = 20;

    private Menu menu;

    private Arena arena;
    private TetrisBlock tetris_block;
    private List<Coins> coins = new ArrayList<>();
    int count_to_create_coins = 0;
    private Position position;
    private List<Bomb> bombs = new ArrayList<>();

    private InputHandler inputHandler;
    private TetrisBlockController tetrisBlockController;

    private ScreenManager screenManager;
    private GameView gameView;

    private int score = 0;
    private int level = 1;
    private int score_per_level = 5;
    private int speed_per_level = 50;
    private int initial_speed = 600;
    private long lastBombFallTime = 0;
    private int bombFallSpeed = 600;
    private TetrisBlock nextBlock; // Armazena o próximo bloco

    public List<Bomb> getBomb() {
        return bombs;
    }

    public Game(Menu menu_) {


        this.menu = menu_;

        screenManager = menu_.getScreenManager();

        inputHandler = new InputHandlerForGame(this); // Gerencia entradas

        arena = new Arena(columns, rows); // Inicializa a arena

        tetris_block = TetrisBlockFactory.createBlock(columns, rows);

        nextBlock = TetrisBlockFactory.createBlock(columns, rows);

        gameView = new GameView(this);

        tetrisBlockController = new TetrisBlockController(this);

    }

    public int get_Additional_Points() {
        int points = 0;
        for (Coins coin_ : coins) {
            if (coin_.isCollected()) {
                points += coin_.getValue();
            }
        }
        return points;
    }

    public List<Coins> getCoins() {
        return coins;
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

    public TetrisBlock getNextBlock() {
        return nextBlock;
    }

    public boolean can_level_up() {
        int can_up_level = score / score_per_level + 1;

        if (can_up_level > level) {
            return true;
        }

        return false;
    }


        /*current_score = game.getScore();
            int can_up_level = current_score / score_per_level + 1;

            if(can_up_level > level){
                System.out.println("Level Up!,increasing speed...\n");
                level = can_up_level;
                pause -= speed_per_level;
            }*/


    public boolean continuousBlockFall(Position position) { //(para a thread)

        if (arena.canMoveDown(tetris_block)) {
            tetris_block.setPosition(position);
            if (!coins.isEmpty()) {
                arena.try_Collect_Coin(coins, tetris_block);
            }

            return true;
        } else {

            arena.moveBlocktoBackground(tetris_block);
            score += arena.clearLines() * 5; //caso alguma linha esteja completa, é limpa
            tetris_block = null;
            return false;
        }
    }

    public boolean updateGameState() {

        if (arena.isBlockOutBounds()) {
            gameOver();
            System.out.println("Game Stopped!: Row: " + tetris_block.getPosition().getRow_identifier() + " ------ " + "Column: " + tetris_block.getPosition().getColumn_identifier());

            return false;
        }

        manageBombs();
        checkBombCollisions();

        if (tetris_block == null) {
            tetris_block = nextBlock;
            nextBlock = TetrisBlockFactory.createBlock(columns, rows);
            count_to_create_coins++;
            if (count_to_create_coins % 3 == 0) {
                System.out.println("Coin created!\n");
                coins.add(CoinsFactory.createCoin(arena));
            }

        }

        if (tetris_block == null || !continuousBlockFall(tetrisBlockController.moveDown())) {
            arena.moveBlocktoBackground(tetris_block);
            score += arena.clearLines() * 5; // Clear lines and update score


        }

        // Atualizar a renderização
        try {
            gameView.render();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private void gameOver() {
        System.out.println("Game Over!");
        menu.setCurr_state(GAME_OVER);
        MenuGame_OverView gameOverView = new MenuGame_OverView(screenManager);
        try {

            gameOverView.draw();
            screenManager.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }


    public void manageBombs() {
        bombs.removeIf(Bomb::isExpired);
        if (bombs.isEmpty() && new Random().nextInt(100) < 5) { // 20% chance to spawn a bomb
            Bomb newBomb = BombFactory.createBomb(arena);
            bombs.add(newBomb);

        }
    }

    private void checkBombCollisions() {
        if (tetris_block != null) {
            List<Bomb> bombsToRemove = new ArrayList<>();
            for (Bomb bomb : bombs) {
                if (arena.isBombTouched(bomb, tetris_block)) {
                    bomb.explode(arena.getBackground());
                    bombsToRemove.add(bomb);
                    tetris_block = null; // Remove the block
                    break; // Exit the loop as the block is removed
                }
            }
            bombs.removeAll(bombsToRemove);
        }
    }

    public void moveBlock(Position position, KeyType key) {
        if (key == ArrowRight && !arena.canMoveRight(tetris_block)) {
            return;
        }
        if (key == ArrowLeft && !arena.canMoveLeft(tetris_block)) {
            return;
        }

        if(key == ArrowDown && !arena.canMoveDown(tetris_block)){
            return;
        }

        tetris_block.setPosition(position);
        if(!coins.isEmpty()){
            arena.try_Collect_Coin(coins,tetris_block);
        }

    }

    public void handleInput() throws IOException {
        KeyStroke key = screenManager.readInput();
        inputHandler.processInput(key);
    }


    public void run() throws IOException {
        GameThread gameThread = new GameThread(this); // Passa a instância atual de tetronix.Game
        gameThread.start(); // Inicia a thread

        while (true) {
            if (menu.getCurr_state() == GAME_OVER) {
                return; //prototype
            }
            handleInput();
            gameView.render();
        }
    }

}
