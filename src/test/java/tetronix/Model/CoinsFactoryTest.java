package tetronix.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoinsFactoryTest {

    private Arena mockArena;

    @BeforeEach
    void setUp() {
        mockArena = mock(Arena.class);
    }

    @Test
    void testCreateCoin_ValidCoinGenerated() {
        int rows = 20;
        int cols = 10;
        int highestRow = 15;

        // Mock para simular a arena
        when(mockArena.getRows()).thenReturn(rows);
        when(mockArena.getColumns()).thenReturn(cols);
        when(mockArena.getHighestOccupiedRow()).thenReturn(highestRow);

        // Mock do background vazio
        String[][] background = new String[rows][cols];
        when(mockArena.getBackground()).thenReturn(background);

        Coins coin = CoinsFactory.createCoin(mockArena);

        // Validações da moeda gerada
        assertNotNull(coin, "A moeda não deve ser nula.");
        assertNotNull(coin.getPosition(), "A posição da moeda não deve ser nula.");
        assertTrue(coin.getValue() >= 1 && coin.getValue() <= 4, "O valor da moeda deve estar entre 1 e 4.");
        assertTrue(coin.getColor().equals("yellow") || coin.getColor().equals("gold"), "A cor da moeda deve ser 'yellow' ou 'gold'.");

        // Validações da posição
        Position position = coin.getPosition();
        assertTrue(position.getRow_identifier() >= 0 && position.getRow_identifier() <= highestRow,
                "A linha da moeda deve estar dentro do intervalo válido.");
        assertTrue(position.getColumn_identifier() >= 0 && position.getColumn_identifier() < cols,
                "A coluna da moeda deve estar dentro do intervalo válido.");
    }

    @Test
    void testCreateCoin_CreatesCoinAtEmptyPosition() {
        int rows = 20;
        int cols = 10;
        int highestRow = 15;

        // Mock para simular a arena
        when(mockArena.getRows()).thenReturn(rows);
        when(mockArena.getColumns()).thenReturn(cols);
        when(mockArena.getHighestOccupiedRow()).thenReturn(highestRow);

        // Mock do background onde uma célula específica está ocupada
        String[][] background = new String[rows][cols];
        background[5][5] = "occupied"; // Posição ocupada
        when(mockArena.getBackground()).thenReturn(background);

        Coins coin = CoinsFactory.createCoin(mockArena);

        // Verificar se a moeda foi gerada em uma posição válida
        Position position = coin.getPosition();
        assertNull(background[position.getRow_identifier()][position.getColumn_identifier()],
                "A posição da moeda gerada deve estar vazia no background.");
    }

   /* @Test
    void testGenerateValidPosition_GeneratesCorrectly() {
        int rows = 20;
        int cols = 10;
        int highestRow = 15;

        // Mock do background onde várias células estão ocupadas
        String[][] background = new String[rows][cols];
        background[5][5] = "occupied";
        background[10][7] = "occupied";

        Position position = Mockito.mock(Position.class);
        for (int i = 0; i < 100; i++) {  // Realizar múltiplas iterações para verificar a validade
            Position generatedPosition = CoinsFactory.createCoin(mockArena).getPosition();
            int row = generatedPosition.getRow_identifier();
            int col = generatedPosition.getColumn_identifier();

            assertTrue(row >= 0 && row <= highestRow,
                    "A linha da posição gerada deve estar dentro do intervalo permitido.");
            assertTrue(col >= 0 && col < cols,
                    "A coluna da posição gerada deve estar dentro do intervalo permitido.");
            assertNull(background[row][col], "A posição gerada deve estar vazia.");
        }
    }*/

    @Test
    void testMultipleCoinsHaveDifferentPositions() {
        int rows = 20;
        int cols = 10;
        int highestRow = 15;

        // Mock para simular a arena
        when(mockArena.getRows()).thenReturn(rows);
        when(mockArena.getColumns()).thenReturn(cols);
        when(mockArena.getHighestOccupiedRow()).thenReturn(highestRow);

        // Mock do background vazio
        String[][] background = new String[rows][cols];
        when(mockArena.getBackground()).thenReturn(background);

        Coins coin1 = CoinsFactory.createCoin(mockArena);
        Coins coin2 = CoinsFactory.createCoin(mockArena);

        // As moedas devem ter posições diferentes na maioria das vezes
        assertNotEquals(coin1.getPosition(), coin2.getPosition(),
                "Moedas consecutivas devem ter posições diferentes, sempre que possível.");
    }
}
