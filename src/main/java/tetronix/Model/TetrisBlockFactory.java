package tetronix.Model;

import tetronix.Model.BlockType.*;

import java.util.Random;

public class TetrisBlockFactory {
    private static Random random = new Random();
    private static String [] colors = {"green","red","blue"};

    public static void setRandom(Random random) {TetrisBlockFactory.random = random;}

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
}