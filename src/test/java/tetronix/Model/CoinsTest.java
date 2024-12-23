package tetronix.Model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoinsTest {

    @Test
    void testConstructorAndGetValue() {
        Position position = new Position(5, 5); // Criando uma posição fictícia
        Coins coin = new Coins(position, "yellow", 10); // Criando uma moeda com valor 10 e cor "yellow"

        // Verificar se a moeda foi criada corretamente
        assertNotNull(coin, "A moeda não pode ser null.");
        assertEquals(10, coin.getValue(), "O valor da moeda deve ser 10.");
        assertEquals("yellow", coin.getColor(), "A cor da moeda deve ser 'yellow'."); // Verificar cor, herdada da classe Element
    }

    @Test
    void testIsCollectedInitially() {
        Position position = new Position(5, 5); // Criando uma posição fictícia
        Coins coin = new Coins(position, "yellow", 10); // Criando uma moeda com valor 10

        // Verificar se a moeda não foi coletada inicialmente
        assertFalse(coin.isCollected(), "A moeda não deve ser coletada inicialmente.");
    }

    @Test
    void testCollect() {
        Position position = new Position(5, 5); // Criando uma posição fictícia
        Coins coin = new Coins(position, "yellow", 10); // Criando uma moeda com valor 10

        // Coletar a moeda
        coin.collect();

        // Verificar se a moeda foi coletada corretamente
        assertTrue(coin.isCollected(), "A moeda deve ser coletada após a chamada do método collect.");
    }

    @Test
    void testCollectOnlyOnce() {
        Position position = new Position(5, 5); // Criando uma posição fictícia
        Coins coin = new Coins(position, "yellow", 10); // Criando uma moeda com valor 10

        // Coletar a moeda duas vezes
        coin.collect();
        coin.collect(); // Chamada repetida, não deve ter efeito

        // Verificar se a moeda foi coletada apenas uma vez
        assertTrue(coin.isCollected(), "A moeda deve ser coletada apenas uma vez.");
    }
}
