import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static com.googlecode.lanterna.input.KeyType.*;

public class Game {
    private Screen screen;
    private Arena arena;
    private TetrisBlock tetris_block;
    private Position position;
    private int rows = 40;
    private int columns = 40;


    public Game(){
        try {
            // Criação do terminal
            TerminalSize terminalSize = new TerminalSize(columns, rows);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            // Criação da tela
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // Desabilitar o cursor
            screen.startScreen(); // Iniciar a tela

            arena = new Arena(columns,rows);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TetrisBlock spawnBlocks(){
        int[][] shape = {{1, 0}, {1, 0}, {1, 1}};
        int spawn_y = -shape.length;    //no GameThread, começa a cair de uma posição acima do limite da tela
        int spawn_x = (rows - shape[0].length) / 2;     //posicionado no meio da tela

        position = new Position(spawn_x,spawn_y);
        tetris_block = new TetrisBlock(shape,"#990000",position, columns,rows);

        return tetris_block;
    }




    public boolean continuousBlockFall(Position position){ //(para a thread)
        if(!tetris_block.isAtBottomEdge()){
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


    public void inputMoveBlock(KeyStroke key) throws IOException {
            switch(key.getKeyType()){
                case ArrowUp:
                    tetris_block.rotateBlock();
                    break;
                case ArrowDown:
                    moveBlock(tetris_block.dropBlock(),ArrowDown);
                    break;
                case ArrowLeft:
                    moveBlock(tetris_block.moveLeft(),ArrowLeft);
                    break;
                case ArrowRight:
                    moveBlock(tetris_block.moveRight(),ArrowRight);
                    break;
                default:
                    break;
            }
            draw();
    }


    //Desnhar na tela
    public void draw() throws IOException{
        screen.clear();
        arena.draw(screen.newTextGraphics());
        tetris_block.draw(screen.newTextGraphics());
        screen.refresh();
    }



    public void run() throws IOException {
        GameThread gameThread = new GameThread(this); // Passa a instância atual de Game
        gameThread.start(); // Inicia a thread

        while(true){
            KeyStroke key = screen.readInput();
            if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
                screen.close();
                break;
            } else {
                inputMoveBlock(key);
            }
        }

    }
}
