package tetronix.Model;
import org.junit.jupiter.api.Test;
import tetronix.Model.Arena;
import tetronix.Model.Bomb;
import tetronix.Model.BombFactory;
import tetronix.Model.Position;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BombFactoryTest {

    @Test
    void testCreateBomb() {
        // Mock da Arena
        Arena mockArena = mock(Arena.class);

        // Definindo comportamentos esperados
        when(mockArena.getHighestOccupiedRow()).thenReturn(10); // Assume que a linha mais alta ocupada é 10
        when(mockArena.getColumns()).thenReturn(5); // Assume que a arena tem 5 colunas

        // Chamada do método a ser testado
        Bomb bomb = BombFactory.createBomb(mockArena);

        // Verificando se a bomba foi criada corretamente
        assertNotNull(bomb, "A bomba não pode ser null.");

        // Verificando a cor da bomba
        String[] validColors = {"yellow", "orange", "purple", "white"};
        assertTrue(java.util.Arrays.asList(validColors).contains(bomb.getColor()), "A cor da bomba não é válida.");

        // Verificando a posição da bomba
        Position bombPosition = bomb.getPosition();
        assertTrue(bombPosition.getRow_identifier() >= 0 && bombPosition.getRow_identifier() <= 10, "A linha da bomba deve estar entre 0 e 10.");
        assertTrue(bombPosition.getColumn_identifier() >= 0 && bombPosition.getColumn_identifier() < 5, "A coluna da bomba deve estar entre 0 e 4.");
    }
}
