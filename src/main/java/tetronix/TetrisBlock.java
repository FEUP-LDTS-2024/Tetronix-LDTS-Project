package tetronix;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;


public class TetrisBlock implements  Drawable{
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
        return new Position(position.getColumn_identifier(), position.getRow_identifier() + 1);

    }


    public Position moveLeft() {
        return new Position(position.getColumn_identifier() - 1, position.getRow_identifier());

    }
    public Position moveRight() {
        return new Position(position.getColumn_identifier() + 1, position.getRow_identifier());
    }

    public void rotateBlock() {
        current_rotation++;
        if(current_rotation > 3) current_rotation = 0;
        shape = possible_shapes[current_rotation];

    }


    public boolean isAtRightEdge(){
        return ((position.getColumn_identifier() + (shape[0].length)) == (columns)); //Erro aqui
    }

    public boolean isAtLeftEdge(){
        return (position.getColumn_identifier() == 0);
    }

    public boolean isAtBottomEdge(){

        return ((shape.length + position.getRow_identifier()) >= rows); //função para verificar se o bloco pode continuar a descer
    }

    public boolean canMoveDown(Arena arena){
        String[][] background = arena.getBackground();

        int blockHeight = shape.length;
        int blockWidth = shape[0].length;
        int blockRow = position.getRow_identifier();
        int blockColumn = position.getColumn_identifier();

        if(isAtBottomEdge()){
            return false;
        }

        // Verificar todas as células do bloco
        for (int c = 0; c < blockWidth; c++) {
            for (int r = blockHeight - 1; r >= 0; r--) { // Começa da parte inferior do bloco
                if (shape[r][c] == 1) { // Apenas verifica células ocupadas
                    int nextRow = blockRow + r + 1;
                    int sameColumn = blockColumn + c;

                    if(blockRow < 0){
                        break;
                    }
                    if (background[nextRow][sameColumn] != null) {
                        // Colisão detectada
                        return false;
                    }
                    break;
                }
            }
        }

        return true;
    }




    public boolean canMoveLeft(Arena arena) {
        String[][] background = arena.getBackground();

        int blockHeight = shape.length;
        int blockWidth = shape[0].length;
        int blockRow = position.getRow_identifier();
        int blockColumn = position.getColumn_identifier();

        // Verificar se o bloco inteiro está na borda esquerda
        if (isAtLeftEdge()) {
            return false; // Não pode se mover para a esquerda
        }

        // Verificar todas as células do bloco para colisões
        for (int r = 0; r < blockHeight; r++) { // Para cada linha
            for (int c = 0; c < blockWidth; c++) { // Para cada coluna
                if (shape[r][c] == 1) { // Encontrar uma célula ocupada
                    int sameRow = blockRow + r;
                    int leftColumn = blockColumn + c - 1;

                    if(blockRow < 0){
                        break;
                    }
                    if (background[sameRow][leftColumn] != null) {
                        return false; // Movimento bloqueado
                    }

                    break; // Verificar apenas a célula mais à esquerda da linha atual
                }
            }
        }

        return true; // Nenhuma colisão detectada, pode mover
    }



    public boolean canMoveRight(Arena arena) {
        String[][] background = arena.getBackground();

        int blockHeight = shape.length;
        int blockWidth = shape[0].length;
        int blockRow = position.getRow_identifier();
        int blockColumn = position.getColumn_identifier();

        if(isAtRightEdge()){
            return false;
        }


        // Verificar todas as linhas
        for (int r = 0; r < blockHeight; r++) {
            for (int c = blockWidth - 1; c >= 0; c--) {
                if (shape[r][c] == 1) {
                    int sameRow = blockRow + r;
                    int rightColumn = blockColumn + c + 1;

                    if(blockRow < 0){
                        break;
                    }
                    if (background[sameRow][rightColumn] != null) {
                        return false; // Colisão detectada
                    }
                    break; // Sai do loop após verificar a célula mais à direita*/
                }
            }
        }

        return true; // Nenhuma colisão detectada
    }


    @Override
    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString(color)); //escolher a cor para desenhar o bloco

        for(int r = 0; r < shape.length; r ++){
            for(int c = 0; c < shape[0].length; c++){
                if(shape[r][c] == 1 ){
                    int correspondent_column = (position.getColumn_identifier() + c) * gridCellsize;
                    int correspondent_row = (position.getRow_identifier() + r) * gridCellsize;

                    graphics.fillRectangle(new TerminalPosition(correspondent_column, correspondent_row), new TerminalSize(gridCellsize, gridCellsize), ' ');
                }
            }
        }
    }
}







