package tetronix.Model;

import tetronix.Model.BlockType.*;

import java.util.Random;

public class TetrisBlockFactory {
    private static Random random = new Random();
    private static String [] colors = {"green","red","blue","black"};

    /*private static final int[][][] shapes = {
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
    };*/







    public static TetrisBlock createBlock(int columns, int rows) {
        int randomIndex = random.nextInt(6); // Número de tipos de blocos disponíveis

        int random_rotation =random.nextInt(3);
        String random_color = colors[random.nextInt(colors.length)];


        switch (randomIndex) {
            case 0:
                return new IBlock(random_color,random_rotation,columns,rows);
            case 1:
                return new TBlock(random_color,random_rotation,columns,rows);
            case 2:
                return new SBlock(random_color,random_rotation,columns,rows);
            case 3:
                return new ZBlock(random_color,random_rotation,columns,rows);
            case 4:
                return new LBlock(random_color,random_rotation,columns,rows);
            case 5:
                return new JBlock(random_color,random_rotation,columns,rows);
            default:
                throw new IllegalArgumentException("Bloco inválido");
        }
    }





    /*public static TetrisBlock createBlock(int columns, int rows) {  //Factory pattern
        //int[][] shape = shapes[random.nextInt(shapes.length)];

        int spawnRow =-shape.length;
        int spawnColumn =random.nextInt(columns - (shape[0].length * 2) - 2);
        int random_rotation =random.nextInt(3);
        String random_color = colors[random.nextInt(colors.length)];


        Position position = new Position(spawnColumn, spawnRow);
        return new TetrisBlock(shape, random_color, position,random_rotation,columns,rows);
    }*/
}