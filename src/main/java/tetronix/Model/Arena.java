package tetronix.Model;


public class Arena {
    private int columns;
    private int rows;
    private TetrisBlock[][] background;
    private int gridCellsize;

    public Arena(int columns_, int rows_){
        this.columns = columns_;
        this.rows = rows_;
        this.gridCellsize = rows_ / columns_;
        background = new TetrisBlock[rows_][columns_];
    }

    public TetrisBlock[][] getBackground(){
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



    public boolean canMoveDown(Object object) {
        if (object == null) return false;

        int[][] shape;
        int blockRow;
        int blockColumn;

        if (object instanceof TetrisBlock) {
            TetrisBlock block = (TetrisBlock) object;
            shape = block.getShape();
            blockRow = block.getPosition().getRow_identifier();
            blockColumn = block.getPosition().getColumn_identifier();
        } else if (object instanceof Bomb) {
            Bomb bomb = (Bomb) object;
            shape = bomb.getShape();
            blockRow = bomb.getPosition().getRow_identifier();
            blockColumn = bomb.getPosition().getColumn_identifier();
        } else {
            return false; // Unsupported object type
        }

        int blockHeight = shape.length;
        int blockWidth = shape[0].length;

        if (blockRow + blockHeight >= rows) {
            return false; // At bottom edge
        }

        // Check for collisions
        for (int c = 0; c < blockWidth; c++) {
            for (int r = blockHeight - 1; r >= 0; r--) {
                if (shape[r][c] == 1) {
                    int nextRow = blockRow + r + 1;
                    int realColumn = blockColumn + c * 2; // Consider width duplication

                    if (nextRow >= 0 && (background[nextRow][realColumn] != null ||
                            (realColumn + 1 < columns && background[nextRow][realColumn + 1] != null))) {
                        return false; // Collision detected
                    }
                    break; // Only need to check the first occupied cell in this column
                }
            }
        }

        return true;
    }


    public boolean canMoveLeft(Object object) {
        if (object == null) return false;

        int[][] shape;
        int blockHeight;
        int blockWidth;
        int blockRow;
        int blockColumn;

        if (object instanceof TetrisBlock) {
            TetrisBlock block = (TetrisBlock) object;
            shape = block.getShape();
            blockHeight = shape.length;
            blockWidth = shape[0].length;
            blockRow = block.getPosition().getRow_identifier();
            blockColumn = block.getPosition().getColumn_identifier();

            if (isAtLeftEdge(block)) {
                return false;
            }
        } else if (object instanceof Bomb) {
            Bomb bomb = (Bomb) object;
            shape = bomb.getShape();
            blockHeight = shape.length;
            blockWidth = shape[0].length;
            blockRow = bomb.getPosition().getRow_identifier();
            blockColumn = bomb.getPosition().getColumn_identifier();

            if (blockColumn == 0) {
                return false;
            }
        } else {
            return false;
        }

        for (int r = 0; r < blockHeight; r++) {
            for (int c = 0; c < blockWidth; c++) {
                if (shape[r][c] == 1) {
                    int sameRow = blockRow + r;
                    int leftColumn = blockColumn + c * 2 - 1;

                    if (blockRow < 0) {
                        break;
                    }

                    if (leftColumn >= 0 && background[sameRow][leftColumn] != null) {
                        return false;
                    }
                    break;
                }
            }
        }

        return true;
    }



    public boolean canMoveRight(Object object) {
        if (object == null) return false;

        int[][] shape;
        int blockHeight;
        int blockWidth;
        int blockRow;
        int blockColumn;

        if (object instanceof TetrisBlock) {
            TetrisBlock block = (TetrisBlock) object;
            shape = block.getShape();
            blockHeight = shape.length;
            blockWidth = shape[0].length;
            blockRow = block.getPosition().getRow_identifier();
            blockColumn = block.getPosition().getColumn_identifier();

            if (isAtRightEdge(block)) {
                return false;
            }
        } else if (object instanceof Bomb) {
            Bomb bomb = (Bomb) object;
            shape = bomb.getShape();
            blockHeight = shape.length;
            blockWidth = shape[0].length;
            blockRow = bomb.getPosition().getRow_identifier();
            blockColumn = bomb.getPosition().getColumn_identifier();

            if (blockColumn + 2 >= columns) {
                return false;
            }
        } else {
            return false;
        }

        for (int r = 0; r < blockHeight; r++) {
            for (int c = blockWidth - 1; c >= 0; c--) {
                if (shape[r][c] == 1) {
                    int sameRow = blockRow + r;
                    int rightColumn = blockColumn + c * 2 + 2;

                    if (blockRow < 0) {
                        break;
                    }

                    if (rightColumn < columns && background[sameRow][rightColumn] != null) {
                        return false;
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
                    background[row_pos + r][realColumn] = new TetrisBlock(color);
                    background[row_pos + r][realColumn + 1] = new TetrisBlock(color);

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

    public boolean isBlockOutBounds(TetrisBlock block) {
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

    public boolean canMoveDown(Bomb bomb) {
        if (bomb == null) return false;
        int[][] shape = bomb.getShape();
        int bombRow = bomb.getPosition().getRow_identifier();
        int bombColumn = bomb.getPosition().getColumn_identifier();

        if (bombRow + 1 >= rows) {
            return false;
        }

        if (background[bombRow + 1][bombColumn] != null) {
            return false;
        }

        return true;
    }
}