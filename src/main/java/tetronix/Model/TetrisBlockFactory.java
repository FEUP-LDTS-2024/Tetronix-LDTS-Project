package tetronix.Model;

public class TetrisBlockFactory {
    public static TetrisBlock createBlock(int columns, int rows) {  //Factory pattern
        int[][] shape = {{1, 0},
                         {1, 0},
                         {1, 1, 1}};

        //int[][] shape = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};



        int spawnRow =-shape.length;
        int spawnColumn = (columns - shape[0].length) / 2;
        Position position = new Position(spawnColumn, spawnRow);
        return new TetrisBlock(shape, "#990000", position);
    }
}