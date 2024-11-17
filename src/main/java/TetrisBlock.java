import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;


public class TetrisBlock {
    private int [][]shape;
    private String color;
    private Position position;
    private int gridCellsize;

    public TetrisBlock(int [][] shape_, String color_,Position position_,int columns_,int rows_){
        this.shape = shape_;
        this.color = color_;
        this.position = position_;
        this.gridCellsize = rows_ / columns_;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    // Métodos de movimentação
    public Position moveUp() {
        return new Position(position.getX(), position.getY() - 1);
        // Subir (diminuir y)
    }

    public Position moveDown() {
        return new Position(position.getX(), position.getY() + 1);
        // Descer (aumentar y)
    }

    public Position moveLeft() {
        return new Position(position.getX() - 1, position.getY());
        // Ir para a esquerda (diminuir x)
    }

    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());
        // Ir para a direita (aumentar x)
    }

    public int getBottomEdge(){
        return (shape.length + position.getY()); //função para verificar se o bloco pode continuar a descer
    }




    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString(color)); //escolher a cor para desenhar o bloco

        for(int row = 0; row < shape.length; row ++){
            for(int col = 0; col < shape[0].length; col++){
                if(shape[row][col] == 1 ){
                    int x = (position.getX() + col) * gridCellsize;
                    int y = (position.getY() + row) * gridCellsize;

                    graphics.fillRectangle(new TerminalPosition(x, y), new TerminalSize(gridCellsize, gridCellsize), ' ');
                }
            }
        }


    }
}







