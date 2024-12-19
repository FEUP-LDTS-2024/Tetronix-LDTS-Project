package tetronix.View;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;

public class NextBlockView implements ElementViewer<TetrisBlock> {
    private Game game;
    private ScreenManager screenManager;
    private TetrisBlock nextBlock;
    private Position previewPosition;

    public NextBlockView(Game game_, GameView gameView){
        this.game=game_;
        this.screenManager = game.getScreenManager();
        previewPosition = new Position(25, 11); // Ajuste os valores conforme necessário
        gameView.addElementToList(this);
    }

    @Override
    public void draw() {
        this.nextBlock = game.getNextBlock();

        if (nextBlock == null) return;

        TextGraphics graphics = screenManager.getTextGraphics();

        graphics.putString(24, 8, "NEXT BLOCK" );

        graphics.setBackgroundColor(TextColor.Factory.fromString("white"));

        graphics.fillRectangle(new TerminalPosition(24, 10), new TerminalSize(10, 6), ' ');

        drawAtPosition();
    }



    private void drawAtPosition() {
        if (nextBlock == null) return;

        TextGraphics graphics = screenManager.getTextGraphics();
        int[][] shape = nextBlock.getShape();
        String color = nextBlock.getColor();

        // Dimensões do retângulo branco
        int rectWidth = 10;
        int rectHeight = 6;

        // Dimensões do bloco
        int blockWidth = shape[0].length * 2; // Cada célula tem largura 2
        int blockHeight = shape.length;

        // Cálculo para centralizar o bloco dentro do retângulo
        int offsetX = previewPosition.getRow_identifier() + (rectHeight - blockHeight) / 2;
        int offsetY = previewPosition.getColumn_identifier() + (rectWidth - blockWidth) / 2;

        graphics.setBackgroundColor(TextColor.Factory.fromString(color));

        // Renderiza o bloco centralizado
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] == 1) {
                    int y = offsetY + c * 2; // Colunas precisam de multiplicação por 2
                    int x = offsetX + r;     // Linhas são incrementadas diretamente
                    graphics.fillRectangle(new TerminalPosition(y - 1, x - 1), new TerminalSize(2, 1), ' ');
                }
            }
        }
    }
}