package tetronix.Model;


import java.util.ArrayList;
import java.util.List;

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

        return ((position.getColumn_identifier() + (shape[0].length)*2) == (columns)); //*2 porque a largura foi duplicada para ter um formato de quadrado
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



    public boolean canMoveDown(TetrisBlock block) {
        if (block == null) return false;

        int[][] shape = block.getShape();
        int blockHeight = shape.length;
        int blockWidth = shape[0].length;
        int blockRow = block.getPosition().getRow_identifier();
        int blockColumn = block.getPosition().getColumn_identifier();

        if (isAtBottomEdge(block)) {
            return false;
        }

        // Verificar todas as células do bloco
        for (int c = 0; c < blockWidth; c++) {
            for (int r = blockHeight - 1; r >= 0; r--) { // Começa da parte inferior do bloco
                if (shape[r][c] == 1) { // Apenas verifica células ocupadas
                    int nextRow = blockRow + r + 1;
                    int realColumn = blockColumn + c * 2; // Considera a duplicação de largura

                    if (blockRow < 0) {
                        break; // Ignorar se o bloco está acima da tela
                    }



                    // Verifica colisão para ambas as colunas ocupadas pela célula lógica
                    if (/*nextRow >= background.length || realColumn + 1 >= background[0].length ||*/
                            (background[nextRow][realColumn] != null) || (background[nextRow][realColumn + 1] != null )) {
                        // Colisão detectada
                        return false;
                    }
                    break; // Só precisa verificar a primeira célula ocupada nessa coluna
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
        if (block == null) return false;

        int[][] shape = block.getShape();
        int blockHeight = shape.length;
        int blockWidth = shape[0].length;
        int blockRow = block.getPosition().getRow_identifier();
        int blockColumn = block.getPosition().getColumn_identifier();

        if (isAtRightEdge(block)) {
            return false;
        }

        // Verificar todas as linhas
        for (int r = 0; r < blockHeight; r++) {
            for (int c = blockWidth - 1; c >= 0; c--) { // Começa da célula mais à direita
                if (shape[r][c] == 1) { // Apenas verifica células ocupadas
                    int sameRow = blockRow + r;
                    int rightColumn = blockColumn + c * 2 + 2; // Considera a duplicação horizontal

                    if (blockRow < 0) {
                        break;
                    }



                    if (/*rightColumn >= background[0].length ||*/ background[sameRow][rightColumn] != null) {
                        return false; // Colisão detectada
                    }
                    break;
                }
            }
        }

        return true;
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

        for (int r = 0; r < number_of_rows; r++) {
            for (int c = 0; c < number_of_columns; c++) {
                if (shape[r][c] == 1) {
                    // Calcula a posição correta para a duplicação
                    int realColumn = column_pos + c * 2; // Duplicação de largura

                    // Preenche ambas as células horizontais
                        background[row_pos + r][realColumn] = color;
                        background[row_pos + r][realColumn + 1] = color;

                }
            }
        }
    }

    private boolean isRowZeroEmpty(){
        for(int c = 0; c < columns; c++){
            if(background[0][c] != null){
                return false;
            }
        }

        return true;
    }

    public boolean isBlockOutBounds() {
        if(!isRowZeroEmpty()) {
            return true;
        }

        return false;
    }

    public boolean canRotate(TetrisBlock block, int newRotation) {

        int[][] rotatedShape = block.getPossible_shapes()[newRotation];
        int rotatedHeight = rotatedShape.length;
        int rotatedWidth = rotatedShape[0].length;

        // Posição atual do bloco
        int blockRow = block.getPosition().getRow_identifier();
        int blockColumn = block.getPosition().getColumn_identifier();


        // Verificar colisões com o background
        for (int r = 0; r < rotatedHeight; r++) {
            for (int c = 0; c < rotatedWidth; c++) {
                if (rotatedShape[r][c] == 1) { // Apenas células ocupadas no bloco rotacionado
                    int arenaRow = blockRow + r;
                    int arenaColumn = blockColumn + c * 2; // Considera duplicação horizontal

                    if(arenaRow < rows && arenaRow > 0 && arenaColumn < columns){
                        if (background[arenaRow][arenaColumn] != null) {
                            return false;
                        }
                    }

                }
            }
        }

        return true; // Nenhuma colisão e dentro dos limites
    }



    public boolean isBlockOutBoundsAfterRotation(TetrisBlock block){
        int widht = block.getShape()[0].length;
        int height = block.getShape().length;
        Position position = block.getPosition();

        // Verificar limites à direita (considera duplicação horizontal)
        if (position.getColumn_identifier() + (widht * 2) > columns) {
            return true;
        }

        //checkground
        if(position.getRow_identifier() + height > rows){
            return true;
        }
        return false;
    }

    public void try_Collect_Coin(List<Coins> coins_, TetrisBlock block){

            for(Coins coin : coins_){
                if(!coin.isCollected()){
                    if(check_BlockOnCoin(coin,block)){
                        coin.collect();
                    }

                }
            }
    }


    public boolean check_BlockOnCoin(Coins coin_, TetrisBlock block_) {

        int[][] shape = block_.getShape();
        int height = shape.length;
        int width = shape[0].length;

        // Posição atual do bloco
        int blockRow = block_.getPosition().getRow_identifier();
        int blockColumn = block_.getPosition().getColumn_identifier();

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (shape[r][c] == 1) {
                    int arenaRow = blockRow + r;
                    int arenaColumnStart = blockColumn + c * 2;  // Início da célula duplicada
                    int arenaColumnEnd = arenaColumnStart + 1;  // Fim da célula duplicada

                    int coinRow = coin_.getPosition().getRow_identifier();
                    int coinColumn = coin_.getPosition().getColumn_identifier();

                    // Verifica se a posição da moeda coincide com a célula duplicada do bloco
                    if (arenaRow == coinRow && coinColumn >= arenaColumnStart && coinColumn <= arenaColumnEnd) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isBombTouched(Bomb bomb, TetrisBlock block) {
        int bombRow = bomb.getPosition().getRow_identifier();
        int bombCol = bomb.getPosition().getColumn_identifier();
        int blockRow = block.getPosition().getRow_identifier();
        int blockCol = block.getPosition().getColumn_identifier();
        int[][] blockShape = block.getShape();

        for (int r = 0; r < blockShape.length; r++) {
            for (int c = 0; c < blockShape[r].length; c++) {
                if (blockShape[r][c] == 1) {
                    int realCol = blockCol + c * 2;
                    if (blockRow + r == bombRow && (realCol == bombCol || realCol + 1 == bombCol)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void handleBombExplosion(Bomb bomb) {
        int bombRow = bomb.getPosition().getRow_identifier();
        int bombCol = bomb.getPosition().getColumn_identifier();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = bombRow + i;
                int newCol = bombCol + j;

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < columns) {
                    background[newRow][newCol] = null;
                }
            }
        }
    }

}