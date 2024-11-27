package tetronix.Model;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;


public class Arena {
    private int columns;
    private int rows;
    private String[][] background;
    private int gridCellsize;

    public Arena(int columns_, int rows_){
        this.columns = columns_;
        this.rows = rows_;
        this.gridCellsize = rows_ / columns_;
        background = new String[rows_][columns_];
    }

    public String[][] getBackground(){
        return this.background;
    }

    public int getRows() {return rows;}

    public int getColumns() {return columns;}


    public boolean isAtRightEdge(TetrisBlock block){
        Position position = block.getPosition();
        int[][] shape = block.getShape();

        return ((position.getColumn_identifier() + (shape[0].length)) == (columns)); //Erro aqui
    }

    public boolean isAtLeftEdge(TetrisBlock block){
        Position position = block.getPosition();

        return (position.getColumn_identifier() == 0);
    }

    public boolean isAtBottomEdge(TetrisBlock block){
        Position position = block.getPosition();
        int[][] shape = block.getShape();

        return ((shape.length + position.getRow_identifier()) >= rows); //função para verificar se o bloco pode continuar a descer
    }



    public boolean canMoveDown(TetrisBlock block){
        if(block == null) return false;
        int [][] shape = block.getShape();
        int blockHeight = block.getShape().length;
        int blockWidth = block.getShape()[0].length;
        int blockRow = block.getPosition().getRow_identifier();
        int blockColumn = block.getPosition().getColumn_identifier();

        if(isAtBottomEdge(block)){
            return false;
        }

        // Verificar todas as células do bloco
        for (int c = 0; c < blockWidth; c++) {
            for (int r = blockHeight - 1; r >= 0; r--) { // Começa da parte inferior do bloco
                if (shape[r][c] == 1) { // Apenas verifica células ocupadas
                    int nextRow = blockRow + r + 1;
                    int sameColumn = blockColumn + c;

                    /*if(blockRow < 0){
                        break;
                    }*/
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


    public boolean canMoveLeft(TetrisBlock block) {
        if(block == null) return false;
        int [][] shape = block.getShape();
        int blockHeight = block.getShape().length;
        int blockWidth = block.getShape()[0].length;
        int blockRow = block.getPosition().getRow_identifier();
        int blockColumn = block.getPosition().getColumn_identifier();

        // Verificar se o bloco inteiro está na borda esquerda
        if (isAtLeftEdge(block)) {
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



    public boolean canMoveRight(TetrisBlock block) {
        if(block == null){ return false;}
        int [][] shape = block.getShape();
        int blockHeight = block.getShape().length;
        int blockWidth = block.getShape()[0].length;
        int blockRow = block.getPosition().getRow_identifier();
        int blockColumn = block.getPosition().getColumn_identifier();

        if(isAtRightEdge(block)){
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

    public int clearLines(){
        int cleared_lines = 0;

        //Econtrar linhas completas
        for(int r = rows - 1; r >= 0; r--){ //pesquisa de baixo para cima
            boolean complete_line = true;

            for(int c = 0; c < columns; c++){ //percorrer todas as colunas de uma linha
                if(background[r][c] == null){ // se houver uma célula que n esteja preenchida, passara para next Row
                    complete_line = false;
                    break;
                }
            }

            if(complete_line){ //se a linha estiver completa, meter todas as células a null
                cleared_lines++;
                clearCompleteLine(r);
                shiftDown(r);
                clearCompleteLine(0);
                r++;

            }
        }

        return cleared_lines;
    }


    private void clearCompleteLine(int row_to_clear){
        for(int c = 0; c < columns; c++){
            background[row_to_clear][c] = null;
        }
    }

    private void shiftDown(int row_){
        for(int r = row_; r > 0; r--){
            for(int c = 0; c < columns; c++){

                background[r][c] = background[r - 1][c];
            }
        }
    }


    public void moveBlocktoBackground(TetrisBlock block){
        if(block == null) return;
        int [][]shape = block.getShape();
        int number_of_rows = block.getShape().length;
        int number_of_columns = block.getShape()[0].length;
        int column_pos = block.getPosition().getColumn_identifier();
        int row_pos = block.getPosition().getRow_identifier();
        String color = block.getColor();

        while(row_pos < 0){
            row_pos++; //para mover o último bloco para arena, caso contrário, se o jogo acabar e carregar arrowRight,o bloco desaparece todo
        }

        for (int r = 0; r < number_of_rows ; r++) {
            for (int c = 0; c < number_of_columns; c++) {
                if(shape[r][c] == 1){
                    background[row_pos + r][column_pos + c] = color;
                }
            }
        }
    }

    public boolean isBlockOutBounds(TetrisBlock block) {
        if (block.getPosition().getRow_identifier() < 0) {
            return true;
        }

        return false;
    }
}



