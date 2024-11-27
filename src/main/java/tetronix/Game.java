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
    private int rows = 40;
    private int columns = 40;

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

        gameView = new GameView();

        tetrisBlockController = new TetrisBlockController();

    }

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
            return false;
        }
    }

    public void updateGameState() {
        if (tetris_block == null || !continuousBlockFall(tetrisBlockController.moveDown(tetris_block))) {
            tetris_block = TetrisBlockFactory.createBlock(columns, rows);

        }

        // Atualizar a renderização
        try {
            gameView.render(arena, tetris_block, screenManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            gameView.render(arena,tetris_block,screenManager);
        }
    }

}
