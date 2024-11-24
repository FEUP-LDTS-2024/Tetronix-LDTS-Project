package tetronix;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static com.googlecode.lanterna.input.KeyType.*;

public class Game {
    private ScreenManager screenManager;
    private Arena arena; //modified to public for test use
    private TetrisBlock tetris_block; //modified to public for test use
    private InputHandler inputHandler;
    private Position position;
    private int rows = 40;
    private int columns = 40;


    public Game() {
        try {
            screenManager = new ScreenManager(this); // Gerencia a tela
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        inputHandler = new InputHandler(this); // Gerencia entradas
        arena = new Arena(columns, rows); // Inicializa a arena
    }

    public TetrisBlock getTetris_block() {
        return tetris_block;
    }

    public void setTetris_block(TetrisBlock tetris_block) {
        this.tetris_block = tetris_block;
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

        if(tetris_block.canMoveDown(arena)){
            tetris_block.setPosition(position);
            return true;
        } else {
            arena.moveBlocktoBackground(tetris_block);
            return false;
        }
    }

    public void moveBlock(Position position,KeyType key){
        if(tetris_block.isAtRightEdge() && key == ArrowRight){
            return;
        }
        if(tetris_block.isAtLeftEdge() && key == ArrowLeft){
            return;
        }

        tetris_block.setPosition(position);
    }


    public void dropBlock() {
        // Enquanto o bloco não atingir o fundo ou não houver espaço ocupado abaixo dele
        while (tetris_block.canMoveDown(arena)) {
            // Mover o bloco para baixo
            tetris_block.setPosition(tetris_block.moveDown());
        }

        // Após o bloco chegar ao fundo ou ser bloqueado, movê-lo para a arena
        arena.moveBlocktoBackground(tetris_block);
    } //AQUI OU NA CLASSE TETRIS?

    
    //Desenhar na tela
    public void renderImage() throws IOException {
        screenManager.clear();

        Drawable[] drawableElements = {arena, tetris_block};
        for (Drawable drawable : drawableElements) {
            drawable.draw(screenManager.getTextGraphics());
        }

        screenManager.refresh();
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
            renderImage();
        }

    }
}
