package tetronix.Model;

import java.util.Random;

public class BombFactory {
    private static Random random = new Random();
    private static String[] colors = {"yellow", "orange", "purple", "white"};

    public static Bomb createBomb(Arena arena) {
        String random_color = colors[random.nextInt(colors.length)];
        int highestRow = arena.getHighestOccupiedRow();
        int spawnRow = random.nextInt(highestRow + 1); // +1 to include the highest row
        int spawnColumn = random.nextInt(arena.getColumns());
        Position position = new Position(spawnColumn, spawnRow);
        Bomb bomb = new Bomb(random_color, position, arena.getColumns(), arena.getRows());
        return bomb;
    }

}

