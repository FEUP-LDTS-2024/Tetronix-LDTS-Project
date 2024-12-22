package tetronix.Control;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetronix.Game;
import tetronix.Model.Arena;
import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TetrisBlockControllerTest {
    private Game mockGame;
    private Arena mockArena;
    private TetrisBlock mockBlock;
    private TetrisBlockController controller;

    @BeforeEach
    void setUp() {
        mockGame = mock(Game.class); // Mock do Game
        mockArena = mock(Arena.class); // Mock da Arena
        mockBlock = mock(TetrisBlock.class); // Mock do TetrisBlock

        when(mockGame.getTetris_block()).thenReturn(mockBlock); // Configura o retorno do bloco no Game
        controller = new TetrisBlockController(mockGame); // Instancia o Controller
    }

    @Test
    void testMoveDown_ValidBlock() {
        // Configurar comportamento inicial
        Position initialPosition = new Position(5, 10);
        Position expectedPosition = new Position(5, 11);

        when(mockBlock.getPosition()).thenReturn(initialPosition);

        // Executar o método
        Position newPosition = controller.moveDown();

        // Verificar o resultado
        assertEquals(expectedPosition.getColumn_identifier(), newPosition.getColumn_identifier());
        assertEquals(expectedPosition.getRow_identifier(), newPosition.getRow_identifier());
    }

    @Test
    void testMoveLeft_ValidBlock() {
        // Configurar comportamento inicial
        Position initialPosition = new Position(6, 10);
        Position expectedPosition = new Position(4, 10);

        when(mockBlock.getPosition()).thenReturn(initialPosition);

        // Executar o método
        Position newPosition = controller.moveLeft();

        // Verificar o resultado
        assertEquals(expectedPosition.getColumn_identifier(), newPosition.getColumn_identifier());
        assertEquals(expectedPosition.getRow_identifier(), newPosition.getRow_identifier());
    }

    @Test
    void testMoveRight_ValidBlock() {
        // Configurar comportamento inicial
        Position initialPosition = new Position(6, 10);
        Position expectedPosition = new Position(8, 10);

        when(mockBlock.getPosition()).thenReturn(initialPosition);

        // Executar o método
        Position newPosition = controller.moveRight();

        // Verificar o resultado
        assertEquals(expectedPosition.getColumn_identifier(), newPosition.getColumn_identifier());
        assertEquals(expectedPosition.getRow_identifier(), newPosition.getRow_identifier());
    }

    @Test
    void testRotateBlock_ValidRotation() {
        int [][] shape = {{1,0},
                {1,0}};
        // Configurar comportamento inicial
        when(mockBlock.getCurrent_rotation()).thenReturn(1);
        when(mockArena.canRotate(mockBlock, 2)).thenReturn(true);
        when(mockArena.isBlockOutBoundsAfterRotation(mockBlock)).thenReturn(false);

        // Configurar o mock do game
        when(mockGame.getArena()).thenReturn(mockArena);
        when(mockGame.getTetris_block()).thenReturn(mockBlock);
        when(mockBlock.getShape()).thenReturn(shape);

        // Executar o método
        controller.rotateBlock();

        // Verificar se a rotação foi atualizada
        verify(mockBlock).setCurrent_rotation(2);
        verify(mockArena).isBlockOutBoundsAfterRotation(mockBlock); // Permite a chamada
        verify(mockBlock, never()).CorrectPositionAfterRotation(); // Bounds não ativou correção
    }


}

