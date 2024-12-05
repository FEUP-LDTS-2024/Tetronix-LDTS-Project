package tetronix.Model;

import java.util.Random;

public class TetrisBlockFactory {
    private static Random random = new Random();
    private static String[] colors = {"green", "red", "blue", "black"};

    private static final int[][][] shapes = {
            // I Shape
            {
                    {1, 1, 1, 1}
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


    public static TetrisBlock createBlock(int columns, int rows) {  //Factory pattern
        int[][] shape = shapes[random.nextInt(shapes.length)];

        int spawnRow = -shape.length;
        int spawnColumn = random.nextInt(columns - shape[0].length);
        int random_rotation = random.nextInt(3);
        String random_color = colors[random.nextInt(colors.length)];


        Position position = new Position(spawnColumn, spawnRow);
        return new TetrisBlock(shape, random_color, position, random_rotation, columns, rows);
    }
}