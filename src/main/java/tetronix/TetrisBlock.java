package tetronix;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;


public class TetrisBlock {
    private int [][]shape;
    private int [][][] possible_shapes;
    private int current_rotation;
    private String color;
    private Position position;
    private int gridCellsize;
    private int rows;
    private int columns;

    public TetrisBlock(int [][] shape_, String color_,Position position_,int columns_,int rows_){
        this.shape = shape_;
        this.color = color_;
        this.position = position_;
        this.gridCellsize = rows_ / columns_;
        this.rows = rows_;
        this.columns = columns_;
        initShapes();
        current_rotation = 0;

    }

    private void initShapes(){
        possible_shapes = new int [4][][];

        for(int i = 0; i < 4; i++){
            int new_r = shape[0].length; //90º: número de rows do novo array = número de colunas do original
            int new_c = shape.length;
            possible_shapes[i] = new int [new_r][new_c];

            for(int y = 0; y  < new_r; y++){
                for(int x = 0; x < new_c; x++){
                    //meter valores no novo array
                    possible_shapes[i][y][x] = shape[new_c - x - 1][y];
                }
            }
            shape = possible_shapes[i];
        }
    }


    public int[][] getShape() {
        return shape;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public String getColor(){
        return this.color;
    }

    // Métodos de movimentação e rotação
    public Position moveDown() {
        return new Position(position.getX(), position.getY() + 1);
        // Descer (aumentar y)
    }

    public Position dropBlock() {
        System.out.println("Arrow Down pressed, entering loop...\n");
        while(!isAtBottomEdge()){
            position = moveDown();
        }
        return  position;
    }

    public Position moveLeft() {
        return new Position(position.getX() - 1, position.getY());
        // Ir para a esquerda (diminuir x)
    }

    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());
        // Ir para a direita (aumentar x)
    }

    public void rotateBlock() {
        current_rotation++;
        if(current_rotation > 3) current_rotation = 0;
        shape = possible_shapes[current_rotation];
        //problema aqui
    }

    public boolean isAtBottomEdge(){

        return ((shape.length + position.getY()) == rows); //função para verificar se o bloco pode continuar a descer
    }

    public boolean isAtRightEdge(){
        return ((position.getX() + (shape[0].length)) == (columns)); //Erro aqui
    }

    public boolean isAtLeftEdge(){
        return (position.getX() == 0);
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







