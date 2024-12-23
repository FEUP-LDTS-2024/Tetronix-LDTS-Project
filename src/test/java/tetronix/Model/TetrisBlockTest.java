package tetronix.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

class TetrisBlockTest {

    private TetrisBlock tetrisBlock;
    private Position position;

    @BeforeEach
    void setUp() {
        // Posições iniciais para o teste
        position = new Position(5, 5);

        // Forma de uma peça T (exemplo)
        int[][] shape = {
                {1, 1, 1},
                {0, 1, 0}
        };
        String color = "blue";
        int rotation = 0;
        int columns = 10;
        int rows = 20;

        // Criar o TetrisBlock com esses parâmetros
        tetrisBlock = new TetrisBlock(shape, color, position, rotation, columns, rows);
    }

    @Test
    void testInitialization() {
        // Verificar se o TetrisBlock foi inicializado corretamente
        assertNotNull(tetrisBlock, "O TetrisBlock deve ser inicializado.");
        assertEquals(0, tetrisBlock.getCurrent_rotation(), "A rotação inicial deve ser 0.");
        assertEquals(4, tetrisBlock.getPossible_shapes().length, "Deve haver 4 formas possíveis para a rotação.");
    }

    @Test
    void testRotation() {
        // Verificar as rotações
        tetrisBlock.setCurrent_rotation(1);
        int[][] rotatedShape = tetrisBlock.getShape();

        // Verificar se a rotação mudou a forma da peça (após 90 graus)
        assertNotNull(rotatedShape, "A forma rotacionada não deve ser nula.");
    }

    @Test
    void testCorrectPositionAfterRotation() {
        // Testar a correção de posição após rotação (mover a peça para dentro da arena)
        tetrisBlock.setCurrent_rotation(1); // Rotaciona a peça
        tetrisBlock.CorrectPositionAfterRotation(); // Corrige a posição se necessário

        Position correctedPosition = tetrisBlock.getPosition();
        assertTrue(correctedPosition.getColumn_identifier() >= 0, "A posição corrigida deve estar dentro dos limites da coluna.");
        assertTrue(correctedPosition.getRow_identifier() >= 0, "A posição corrigida deve estar dentro dos limites da linha.");
    }

    @Test
    void testGenerateValidColumn() {
        // Testar a geração de coluna válida
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt())).thenReturn(4); // Simulando que a coluna gerada seja 4

        // Usando a função estática para gerar uma coluna válida
        int[][] shape = {
                {1, 1},
                {1, 1}
        };
        int column = TetrisBlock.generateValidColumn(mockRandom, shape, 10);
        assertEquals(4, column, "A coluna gerada deve ser 4.");
    }

    @Test
    void testPositionCorrectionRightBoundary() {
        // Testar se a posição é corrigida no limite direito
        int[][] shape = {
                {1, 1},
                {1, 1}
        };
        Position positionRight = new Position(8, 5); // Posição no limite direito
        TetrisBlock blockRight = new TetrisBlock(shape, "red", positionRight, 0, 10, 20);

        blockRight.CorrectPositionAfterRotation();

        // Verificar se a posição foi corrigida para dentro da arena
        Position correctedPosition = blockRight.getPosition();
        assertTrue(correctedPosition.getColumn_identifier() < 8, "A posição no limite direito foi corrigida.");
    }

    @Test
    void testPositionCorrectionLeftBoundary() {
        // Testar se a posição é corrigida no limite esquerdo
        int[][] shape = {
                {1, 1},
                {1, 1}
        };
        Position positionLeft = new Position(-1, 5); // Posição no limite esquerdo
        TetrisBlock blockLeft = new TetrisBlock(shape, "green", positionLeft, 0, 10, 20);

        blockLeft.CorrectPositionAfterRotation();

        // Verificar se a posição foi corrigida para dentro da arena
        Position correctedPosition = blockLeft.getPosition();
        assertTrue(correctedPosition.getColumn_identifier() >= 0, "A posição no limite esquerdo foi corrigida.");
    }

    @Test
    void testPositionCorrectionBottomBoundary() {
        // Testar se a posição é corrigida no limite inferior
        int[][] shape = {
                {1, 1},
                {1, 1}
        };
        Position positionBottom = new Position(5, 18); // Posição no limite inferior
        TetrisBlock blockBottom = new TetrisBlock(shape, "yellow", positionBottom, 0, 10, 20);

        blockBottom.CorrectPositionAfterRotation();

        // Verificar se a posição foi corrigida para dentro da arena
        Position correctedPosition = blockBottom.getPosition();
        assertTrue(correctedPosition.getRow_identifier() < 20, "A posição no limite inferior foi corrigida.");
    }
}
