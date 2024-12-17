package tetronix.Model;

import java.util.Random;

public class TetrisBlock extends Element {
    private int [][]shape;
    private int [][][] possible_shapes;
    private int current_rotation;
    //private String color;
    //private Position position;
    private int rows;
    private int columns;

    public TetrisBlock(int [][] shape_, String color_,Position position_, int rotation_,int columns_,int rows_){

        super(position_, color_);
        this.shape = shape_;
        initShapes();
        current_rotation = rotation_;
        this.columns = columns_;
        this.rows = rows_;
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


    public int[][] getShape() {return possible_shapes[current_rotation];}

    /*public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public String getColor(){
        return this.color;
    }*/

    public void setCurrent_rotation(int current_rotation_) {this.current_rotation = current_rotation_;}

    public int getCurrent_rotation() {return current_rotation;}

    public int[][][] getPossible_shapes() {return possible_shapes;}

    public void setShape(int[][] shape) {this.shape = shape;}

    public void CorrectPositionAfterRotation(){
        //rightbound

            while(this.getPosition().getColumn_identifier() + (getShape()[0].length * 2) > columns){
                Position position1 = new Position(this.getPosition().getColumn_identifier() - 1,this.getPosition().getRow_identifier());
                setPosition(position1);
            }

        //leftbound

        while(this.getPosition().getColumn_identifier() < 0 ){
            Position position1 = new Position(this.getPosition().getColumn_identifier() + 1,this.getPosition().getRow_identifier());
            setPosition(position1);
        }


        //lowerbound
        if(this.getPosition().getRow_identifier() + getShape().length > rows){
            Position position1 = new Position(this.getPosition().getColumn_identifier(),rows - getShape().length);
            setPosition(position1);
        }

    }

    public static int generateValidColumn(Random random,int [][] shape, int columns){
        int spawnColumn;

        while(true){
            spawnColumn = random.nextInt(columns - (shape[0].length * 2) - 2);
            if(spawnColumn % 2 == 0){
                return spawnColumn;
            }
        }
    }
}







