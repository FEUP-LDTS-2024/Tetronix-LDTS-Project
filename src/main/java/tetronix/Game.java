package tetronix;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tetronix.Control.GameThread;
import tetronix.Control.InputHandler;
import tetronix.Control.InputHandlerForGame;
import tetronix.Control.TetrisBlockController;
import tetronix.Model.Bomb;
import tetronix.Model.BombFactory;
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
    private Position position;

    private InputHandler inputHandler;
    private TetrisBlockController tetrisBlockController;

    private ScreenManager screenManager;
    private GameView gameView;

    private int score = 0;
    private int level = 1;
    private int score_per_level = 5;
    private int speed_per_level = 100;
    private int initial_speed = 600;
    // Add a list to store bombs
    private List<Bomb> bombs = new ArrayList<>();
    private boolean isBombFalling = false;

    // Add a method to spawn a bomb
    public void spawnBomb() {
        Bomb bomb = BombFactory.createBomb(columns, rows);
        bombs.add(bomb);
        isBombFalling = true;
        System.out.println("Bomb spawned at: Row " + bomb.getPosition().getRow_identifier() + ", Column " + bomb.getPosition().getColumn_identifier());
    }


    public Game(Menu menu_) {

        this.menu = menu_;

        screenManager = menu_.getScreenManager();

        inputHandler = new InputHandlerForGame(this); // Gerencia entradas

        arena = new Arena(columns, rows); // Inicializa a arena

        tetris_block = TetrisBlockFactory.createBlock(columns,rows);

        gameView = new GameView(this);

        tetrisBlockController = new TetrisBlockController(this);

    }

    public Menu getMenu() {return menu;}

    public int getInitial_speed() {return initial_speed;}

    public int getScore_per_level() {return score_per_level;}

    public int getSpeed_per_level() {return speed_per_level;}

    public int getLevel() {return level;}

    public void setLevel(int level) {this.level = level;}

    public int getScore() {return score;}

    public TetrisBlockController getTetrisBlockController() {return tetrisBlockController;}

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


    public boolean can_level_up(){
        int can_up_level = score / score_per_level + 1;

        if(can_up_level > level){
            return true;
        }

        return  false;
    }


        /*current_score = game.getScore();
            int can_up_level = current_score / score_per_level + 1;

            if(can_up_level > level){
                System.out.println("Level Up!,increasing speed...\n");
                level = can_up_level;
                pause -= speed_per_level;
            }*/



    public boolean continuousBlockFall(Position position){ //(para a thread)

        if(arena.canMoveDown(tetris_block)){
            tetris_block.setPosition(position);
            return true;
        } else {

            arena.moveBlocktoBackground(tetris_block);
            score += arena.clearLines() * 5; //caso alguma linha esteja completa, é limpa
            return false;
        }
    }

    public boolean updateGameState() {
        if (arena.isBlockOutBounds(tetris_block)) {
            arena.moveBlocktoBackground(tetris_block);
            menu.setCurr_state(GAME_OVER);
            return false;
        }

        if (isBombFalling) {
            updateBombs();
            if (bombs.isEmpty()) {
                isBombFalling = false;
            }
        } else {
            if (tetris_block == null || !continuousBlockFall(tetrisBlockController.moveDown())) {
                tetris_block = TetrisBlockFactory.createBlock(columns, rows);
                if (new Random().nextInt(10) == 0) {
                    spawnBomb();
                }
            }
        }

        try {
            gameView.render();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }


    public void moveBlock(Position position,KeyType key){
        if(key == ArrowRight && !arena.canMoveRight(tetris_block)){
            return;
        }
        if(key == ArrowLeft && !arena.canMoveLeft(tetris_block)){
            return;
        }

        tetris_block.setPosition(position);
    }



    public void handleInput() throws IOException {
        KeyStroke key = screenManager.readInput();
        inputHandler.processInput(key);

        // Move the last bomb if it exists
        if (!bombs.isEmpty()) {
            Bomb lastBomb = bombs.get(bombs.size() - 1);
            Position bombPosition = lastBomb.getPosition();

            if (key.getKeyType() == ArrowRight && arena.canMoveRight(lastBomb)) {
                bombPosition.setColumn_identifier(bombPosition.getColumn_identifier() + 1);
            } else if (key.getKeyType() == ArrowLeft && arena.canMoveLeft(lastBomb)) {
                bombPosition.setColumn_identifier(bombPosition.getColumn_identifier() - 1);
            }
        }
    }


    public void run() throws IOException {
        GameThread gameThread = new GameThread(this); // Passa a instância atual de tetronix.Game
        gameThread.start(); // Inicia a thread

        while(true){
            handleInput();
            updateBombs();
            gameView.render();
            if(menu.getCurr_state() == GAME_OVER){
                return; //prototype
            }
        }
    }

    public void updateBombs() {
        List<Bomb> bombsToRemove = new ArrayList<>();
        for (Bomb bomb : bombs) {
            if (arena.canMoveDown(bomb)) {
                Position currentPosition = bomb.getPosition();
                currentPosition.setRow_identifier(currentPosition.getRow_identifier() + 1);
            } else {
                bomb.explode(arena.getBackground());
                bombsToRemove.add(bomb);
            }
        }
        bombs.removeAll(bombsToRemove);
        if (bombs.isEmpty()) {
            isBombFalling = false;
        }
    }

}
