package tetronix.Model;

import java.util.Random;

public class CoinsFactory {
    private static final String[] colors = {"yellow", "gold"}; // Cores possíveis para as moedas
    private static final Random random = new Random();

    public static Coins createCoin(Arena arena) {
        int [] possible_values = {1 , 1 , 2 , 3 , 4};
        int highestRow = arena.getHighestOccupiedRow();
        int rows = arena.getRows();
        int cols = arena.getColumns();
        String[][] background = arena.getBackground();

        Position coinPosition = generateValidPosition(background,cols, highestRow);
        String coinColor = colors[random.nextInt(colors.length)];
        int value = possible_values[random.nextInt(possible_values.length)];

        return new Coins(coinPosition,coinColor,value);
    }



    private static Position generateValidPosition(String[][] background, int cols, int highestRow) {
        int row, col;

        while(true){
            row = random.nextInt(highestRow + 1);  // Gera uma linha aleatória
            col = random.nextInt(cols); // Gera uma coluna aleatória

            if (background[row][col] == null) {
                break;
            }
        }

        return new Position(col, row);
    }
}

