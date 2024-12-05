package tetronix.Model;

public class TetrisBlock {
    private int[][] shape;
    private int[][][] possible_shapes;
    private int current_rotation;
    private String color;
    private Position position;
    private int rows;
    private int columns;

    public TetrisBlock(int[][] shape_, String color_, Position position_, int rotation_, int columns_, int rows_) {
        this.shape = shape_;
        this.color = color_;
        this.position = position_;
        initShapes();
        current_rotation = rotation_;
        this.columns = columns_;
        this.rows = rows_;
    }

    private void initShapes() {
        possible_shapes = new int[4][][];

        for (int i = 0; i < 4; i++) {
            int new_r = shape[0].length; //90º: número de rows do novo array = número de colunas do original
            int new_c = shape.length;
            possible_shapes[i] = new int[new_r][new_c];

            for (int y = 0; y < new_r; y++) {
                for (int x = 0; x < new_c; x++) {
                    //meter valores no novo array
                    possible_shapes[i][y][x] = shape[new_c - x - 1][y];
                }
            }
            shape = possible_shapes[i];
        }
    }


    public int[][] getShape() {
        return possible_shapes[current_rotation];
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public String getColor() {
        return this.color;
    }

    public void setCurrent_rotation(int current_rotation_) {
        this.current_rotation = current_rotation_;
    }

    public int getCurrent_rotation() {
        return current_rotation;
    }

    public int[][][] getPossible_shapes() {
        return possible_shapes;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public void CorrectPositionAfterRotation() {
        //rightbound
        if (position.getColumn_identifier() + getShape()[0].length > columns) {
            Position position1 = new Position(columns - getShape()[0].length, position.getRow_identifier());
            setPosition(position1);
        }

        //lowerbound
        if (position.getRow_identifier() + getShape().length > rows) {
            Position position1 = new Position(position.getColumn_identifier(), rows - getShape().length);
            setPosition(position1);
        }


        //it can overwrite other blocks that are already on the background


    }

}







