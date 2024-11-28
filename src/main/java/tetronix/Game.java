package tetronix;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import tetronix.Control.GameThread;
import tetronix.Control.InputHandler;
import tetronix.Control.TetrisBlockController;
import tetronix.Model.Arena;
import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;
import tetronix.Model.TetrisBlockFactory;
import tetronix.View.*;

import java.io.IOException;

import static com.googlecode.lanterna.input.KeyType.*;

public class Game {
    private int rows = 20;
    private int columns = 20;
    private int score = 0;

    private Arena arena;
    private TetrisBlock tetris_block;
    private Position position;

    private InputHandler inputHandler;
    private TetrisBlockController tetrisBlockController;

    private ScreenManager screenManager;
    private GameView gameView;



    public Game() {
        try {
            screenManager = new ScreenManager(this); // Gerencia a tela
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        inputHandler = new InputHandler(this); // Gerencia entradas

        arena = new Arena(columns, rows); // Inicializa a arena

        tetris_block = TetrisBlockFactory.createBlock(columns,rows);

        gameView = new GameView(this);

        tetrisBlockController = new TetrisBlockController(this);

    }

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



    public boolean continuousBlockFall(Position position){ //(para a thread)

        if(arena.canMoveDown(tetris_block)){
            tetris_block.setPosition(position);
            return true;
        } else {

            arena.moveBlocktoBackground(tetris_block);
            score += arena.clearLines(); //caso alguma linha esteja completa, é limpa
            return false;
        }
    }

    public boolean updateGameState() {

        if(!arena.canMoveDown(tetris_block)){
            if(arena.isBlockOutBounds(tetris_block)){
                System.out.println("Game Stopped!: Row: " + tetris_block.getPosition().getRow_identifier() + " ------ " + "Column: " + tetris_block.getPosition().getColumn_identifier());
                arena.moveBlocktoBackground(tetris_block);
                return false;
            }
        }


        if (tetris_block == null || !continuousBlockFall(tetrisBlockController.moveDown())) {
            tetris_block = TetrisBlockFactory.createBlock(columns, rows);

        }

        // Atualizar a renderização
        try {
            gameView.render();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
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
    }


    public void run() throws IOException {
        GameThread gameThread = new GameThread(this); // Passa a instância atual de tetronix.Game
        gameThread.start(); // Inicia a thread

        while(true){
            handleInput();
            gameView.render();
        }
    }

}
