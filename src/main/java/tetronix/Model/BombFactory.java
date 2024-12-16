package tetronix.Model;

import java.util.Random;

public class BombFactory {
    private static Random random = new Random();
    private static String[] colors = {"yellow", "orange", "purple", "white"};

    public static Bomb createBomb(int columns, int rows) {
        String random_color = colors[random.nextInt(colors.length)];
        int spawnRow = random.nextInt(rows);
        int spawnColumn = random.nextInt(columns);
        Position position = new Position(spawnColumn, spawnRow);
        Bomb bomb = new Bomb(random_color, position, columns, rows);
        System.out.println("Bomb created at: Row " + spawnRow + ", Column " + spawnColumn + " with color " + random_color);
        return bomb;
    }
}

