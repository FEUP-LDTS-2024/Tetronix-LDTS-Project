package tetronix.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoinsFactoryTest {

    private Arena mockArena;
    private String[][] mockBackground;

    @BeforeEach
    void setUp() {
        mockArena = mock(Arena.class);
        mockBackground = new String[20][10]; // Arena de 20 linhas x 10 colunas
        when(mockArena.getRows()).thenReturn(20);
        when(mockArena.getColumns()).thenReturn(10);
        when(mockArena.getBackground()).thenReturn(mockBackground);
    }

    @Test
    void testCreateCoin_ValidCoinCreated() {
        when(mockArena.getHighestOccupiedRow()).thenReturn(15);

        Coins coin = CoinsFactory.createCoin(mockArena);

        assertNotNull(coin, "A moeda criada não deveria ser nula.");
        assertTrue(coin.getPosition().getRow_identifier() <= 15, "A linha da moeda deve estar dentro do limite superior permitido.");
        assertTrue(coin.getPosition().getColumn_identifier() >= 0 && coin.getPosition().getColumn_identifier() < 10,
                "A coluna da moeda deve estar dentro dos limites da arena.");
        assertTrue(coin.getColor().equals("yellow") || coin.getColor().equals("gold"),
                "A cor da moeda deve ser 'yellow' ou 'gold'.");
        assertTrue(coin.getValue() >= 1 && coin.getValue() <= 4,
                "O valor da moeda deve ser um dos valores permitidos (1, 2, 3, 4).");
    }

    @Test
    void testCreateCoin_ValidPositionGenerated() {
        when(mockArena.getHighestOccupiedRow()).thenReturn(10);

        // Preenche algumas posições no background
        mockBackground[5][5] = "filled";
        mockBackground[2][3] = "filled";

        Coins coin = CoinsFactory.createCoin(mockArena);

        assertNotNull(coin.getPosition(), "A posição da moeda não deve ser nula.");
        assertNull(mockBackground[coin.getPosition().getRow_identifier()][coin.getPosition().getColumn_identifier()],
                "A posição gerada deve estar vazia no background.");
    }
}
