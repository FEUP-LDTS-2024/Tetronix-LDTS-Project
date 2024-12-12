package tetronix.Model;

public class Bomb {
    private int[][] shape;
    private String color;
    private Position position;
    private int rows;
    private int columns;

    public Bomb(String color_, Position position_, int columns_, int rows_) {
        this.shape = new int[][]{{1}}; // Bomb is a single block
        this.color = color_;
        this.position = position_;
        this.columns = columns_;
        this.rows = rows_;
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

    public String getColor() {
        return this.color;
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
