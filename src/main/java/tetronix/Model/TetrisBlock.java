package tetronix.Model;

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

    public TetrisBlock(int [][] shape_, String color_,Position position_){
        this.shape = shape_;
        this.color = color_;
        this.position = position_;
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

    public void setCurrent_rotation(int current_rotation) {this.current_rotation = current_rotation;}

    public int getCurrent_rotation() {return current_rotation;}

    public int[][][] getPossible_shapes() {return possible_shapes;}

    public void setShape(int[][] shape) {this.shape = shape;}

    /*
    // Métodos de movimentação e rotação
    public Position moveDown() {
        return new Position(position.getColumn_identifier(), position.getRow_identifier() + 1);

    }

    public Position moveLeft() {
        return new Position(position.getColumn_identifier() - 1, position.getRow_identifier());

    }
    public Position moveRight() {
        return new Position(position.getColumn_identifier() + 1, position.getRow_identifier());
    }

    public void dropBlock() {
        // Enquanto o bloco não atingir o fundo ou não houver espaço ocupado abaixo dele
        while (arena.canMoveDown(this)) {
            // Mover o bloco para baixo
            position = moveDown();
        }

        // Após o bloco chegar ao fundo ou ser bloqueado, movê-lo para a arena
        arena.moveBlocktoBackground(this);
    }

    public void rotateBlock() {
        current_rotation++;
        if(current_rotation > 3) current_rotation = 0;
        shape = possible_shapes[current_rotation];

    } */

}







