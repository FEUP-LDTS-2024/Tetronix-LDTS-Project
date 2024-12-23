package tetronix.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tetronix.Model.BlockType.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TetrisBlockFactoryTest {

    private Random mockRandom;

    @BeforeEach
    void setUp() {
        // Mock para Random
        mockRandom = mock(Random.class);
        TetrisBlockFactory.setRandom(mockRandom);
    }

    @Test
    void testCreateBlock_ValidBlockCreated() {
        // Configuração do comportamento do mock para gerar valores previsíveis
        when(mockRandom.nextInt(6)).thenReturn(2); // Tipo de bloco: SBlock
        when(mockRandom.nextInt(3)).thenReturn(1); // Rotação: 1
        when(mockRandom.nextInt(3)).thenReturn(0); // Cor: "green"

        int columns = 20;
        int rows = 20;

        TetrisBlock block = TetrisBlockFactory.createBlock(columns, rows);

        // Validações do bloco criado
        assertNotNull(block, "O bloco criado não deveria ser nulo.");
        assertEquals(SBlock.class, block.getClass(), "O tipo de bloco deveria ser SBlock.");
        assertEquals("green", block.getColor(), "A cor do bloco deveria ser 'green'.");
    }

    @Test
    void testCreateBlock_AllBlockTypes() {
        int columns = 20;
        int rows = 20;

        for (int i = 0; i < 6; i++) {
            when(mockRandom.nextInt(6)).thenReturn(i); // Tipo de bloco
            when(mockRandom.nextInt(3)).thenReturn(2); // Rotação
            when(mockRandom.nextInt(3)).thenReturn(1); // Cor: "red"

            TetrisBlock block = TetrisBlockFactory.createBlock(columns, rows);

            assertNotNull(block, "O bloco criado não deveria ser nulo.");
            assertEquals("red", block.getColor(), "A cor do bloco deveria ser 'red'.");

            // Valida que o bloco criado é da classe esperada
            switch (i) {
                case 0 -> assertEquals(IBlock.class, block.getClass(), "Deveria ser um IBlock.");
                case 1 -> assertEquals(TBlock.class, block.getClass(), "Deveria ser um TBlock.");
                case 2 -> assertEquals(SBlock.class, block.getClass(), "Deveria ser um SBlock.");
                case 3 -> assertEquals(ZBlock.class, block.getClass(), "Deveria ser um ZBlock.");
                case 4 -> assertEquals(LBlock.class, block.getClass(), "Deveria ser um LBlock.");
                case 5 -> assertEquals(JBlock.class, block.getClass(), "Deveria ser um JBlock.");
            }
        }
    }

    @Test
    void testCreateBlock_InvalidBlockTypeThrowsException() {
        when(mockRandom.nextInt(6)).thenReturn(6); // Tipo inválido

        int columns = 20;
        int rows = 20;

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> TetrisBlockFactory.createBlock(columns, rows),
                "Deveria lançar uma exceção para tipo de bloco inválido.");

        assertEquals("Bloco inválido", exception.getMessage(), "A mensagem da exceção deveria ser 'Bloco inválido'.");
    }
}
