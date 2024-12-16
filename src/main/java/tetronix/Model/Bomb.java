package tetronix.Model;
import tetronix.Model.Element;

public class Bomb extends Element{
    private int[][] shape;
    private String color;
    private Position position;
    private int rows;
    private int columns;
    private long creationTime;
    private static final long LIFESPAN = 5000;

    public Bomb(String color, Position position, int columns, int rows)
    {
        super(position, color);
        this.shape = new int[][]{{1}}; // Bomb is a single block
        this.color = color;
        this.position = position;
        this.columns = columns;
        this.rows = rows;
        this.creationTime = System.currentTimeMillis();

    }

    public boolean isExpired() {
        return System.currentTimeMillis() - creationTime > LIFESPAN;
    }


    public void updateBombPosition(Bomb bomb, int newRow, int newColumn) {
        Position pos = bomb.getPosition();
        pos.setRow_identifier(newRow);
        pos.setColumn_identifier(newColumn);
    }


    public void explode(String[][] grid) {
        int row = position.getRow_identifier();
        int col = position.getColumn_identifier();

        // Clear surrounding blocks in a 3x3 area
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j; // Adjust for single-width columns

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < columns) {
                    grid[newRow][newCol] = null; // Clear the block
                }
            }
        }
    }
}