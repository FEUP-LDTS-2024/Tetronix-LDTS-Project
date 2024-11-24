package tetronix;

import java.util.Random;

public class TetrisBlockFactory {

    // Array of specific block colors
    private static final String[] COLORS = {
            "#FF0000", // Red
            "#00008B", // Dark Blue
            "#87CEEB", // Light Blue
            "#800080", // Purple
            "#FFFF00", // Yellow
            "#FFA500", // Orange
            "#00FF00"  // Green
    };

    // Define all Tetris shapes
    private static final int[][][] SHAPES = {
            // I Shape
            {
                    {1, 1, 1, 1}
            },
            // O Shape
            {
                    {1, 1},
                    {1, 1}
            },
            // T Shape
            {
                    {1, 1, 1},
                    {0, 1, 0}
            },
            // S Shape
            {
                    {0, 1, 1},
                    {1, 1, 0}
            },
            // Z Shape
            {
                    {1, 1, 0},
                    {0, 1, 1}
            },
            // L Shape
            {
                    {1, 0},
                    {1, 0},
                    {1, 1}
            },
            // J Shape
            {
                    {0, 1},
                    {0, 1},
                    {1, 1}
            }
    };

    public static TetrisBlock createBlock(int columns, int rows) {  // Factory pattern
        // Get a random shape and color
        int[][] randomShape = getRandomShape();
        String randomColor = getRandomColor();

        // Calculate the spawn position
        int spawnRow = -randomShape.length;
        int spawnColumn = (columns - randomShape[0].length) / 2;
        Position position = new Position(spawnColumn, spawnRow);

        // Create and return the Tetris block with the random shape and color
        return new TetrisBlock(randomShape, randomColor, position, columns, rows);
    }

    private static String getRandomColor() {
        Random random = new Random();
        int randomIndex = random.nextInt(COLORS.length);
        return COLORS[randomIndex];
    }

    private static int[][] getRandomShape() {
        Random random = new Random();
        int randomIndex = random.nextInt(SHAPES.length);
        return SHAPES[randomIndex];
    }
}
