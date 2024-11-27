import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import tetronix.Model.Arena;
import tetronix.Model.Position;
import tetronix.Model.TetrisBlock;

import static org.junit.jupiter.api.Assertions.*;

class TetrisBlockTest {
    private TetrisBlock block;
    private Arena mockArena;
    private Position startPosition;
    private int[][] shape = {
            {1, 0},
            {1, 1}
    }; // Forma quadrada simples para facilitar o teste
    private int columns = 10;
    private int rows = 20;

    @BeforeEach
    void setUp() {
        // Cria um mock para a Arena
        mockArena = mock(Arena.class);
        // Inicializa um bloco na posição (0, 0)
        startPosition = new Position(0, 0);
        block = new TetrisBlock(shape, "#FF0000", startPosition, columns, rows);
    }

    @Test
    void testMoveDown() {

        Position newPosition = block.moveDown();


        assertEquals(1, newPosition.getRow_identifier(), "The block should move one step down (increment Y).");
        assertEquals(0, newPosition.getColumn_identifier(), "The block should maintain the same column (X).");
    }

    @Test
    void testMoveLeft() {

        Position newPosition = block.moveLeft();


        assertEquals(-1, newPosition.getColumn_identifier(), "The block should move one step left .");
        assertEquals(0, newPosition.getRow_identifier(), "The block should maintain the same row.");
    }

    @Test
    void testMoveRight() {

        Position newPosition = block.moveRight();


        assertEquals(1, newPosition.getColumn_identifier(), "The block should move one step right (increment X).");
        assertEquals(0, newPosition.getRow_identifier(), "The block should maintain the same row (Y).");
    }

    @Test
    void testIsAtRightEdge() {

        block.setPosition(new Position(columns - shape[0].length, 0));


        boolean result = block.isAtRightEdge();


        assertTrue(result, "The block should be at the right edge.");
    }

    @Test
    void testIsNotAtRightEdge() {

        block.setPosition(new Position(columns - shape[0].length - 1, 0));


        boolean result = block.isAtRightEdge();


        assertFalse(result, "The block should not be at the right edge.");
    }

    @Test
    void testIsAtLeftEdge() {

        block.setPosition(new Position(0, 0));


        boolean result = block.isAtLeftEdge();


        assertTrue(result, "The block should be at the left edge.");
    }

    @Test
    void testIsNotAtLeftEdge() {

        block.setPosition(new Position(1, 0));


        boolean result = block.isAtLeftEdge();


        assertFalse(result, "The block should not be at the left edge.");
    }

    @Test
    void testIsAtBottomEdge() {

        block.setPosition(new Position(0, rows - shape.length));


        boolean result = block.isAtBottomEdge();


        assertTrue(result, "The block should be at the bottom edge.");
    }

    @Test
    void testIsNotAtBottomEdge() {

        block.setPosition(new Position(0, rows - shape.length - 1));


        boolean result = block.isAtBottomEdge();


        assertFalse(result, "The block should not be at the bottom edge.");
    }

    @Test
    void testRotateBlock() {

        block.rotateBlock();


        int[][] rotatedShape = block.getShape();
        assertNotNull(rotatedShape, "The shape should not be null after rotation.");
        assertEquals(shape[0].length, rotatedShape.length, "The rotated shape's rows should equal the original shape's columns.");
        assertEquals(shape.length, rotatedShape[0].length, "The rotated shape's columns should equal the original shape's rows.");
    }

    @Test
    void testCanMoveDown_WhenNotAtBottomAndNoCollision() {

        when(mockArena.getBackground()).thenReturn(new String[rows][columns]);
        block.setPosition(new Position(0, 0));


        boolean result = block.canMoveDown(mockArena);


        assertTrue(result, "The block should be able to move down.");
    }

    @Test
    void testCanMoveDown_WhenAtBottomEdge() {

        block.setPosition(new Position(0, rows - shape.length));


        boolean result = block.canMoveDown(mockArena);


        assertFalse(result, "The block should not be able to move down at the bottom edge.");
    }

    @Test
    void testCanMoveDown_WhenCollisionBelow() {

        String[][] background = new String[rows][columns];
        //O bloco é um quadrado de lado 2, se ele na matrix estiver na posição 0, as posições [0,0],[0,1],[1,0],[1,1] vão estar ocupadas
        //por isso, para verificar é preciso ver se o bloco desce quando a linha de índice 2 (3ºlinha)[2,0] está pintada
        background[2][0] = "#FFFFFF"; // Simula um bloco na célula diretamente abaixo
        when(mockArena.getBackground()).thenReturn(background);

        block.setPosition(new Position(0, 0));


        boolean result = block.canMoveDown(mockArena);


        assertFalse(result, "The block should not be able to move down due to a collision below.");
    }

  /*  @Test
    void testIsNextDownPositionOccupied_WhenNoCollision() {

        String[][] background = new String[rows][columns];
        when(mockArena.getBackground()).thenReturn(background);

        block.setPosition(new Position(0, 0));


        boolean result = block.isNextDownPositonOccupied(mockArena);


        assertFalse(result, "The next position below should not be occupied.");
    }

    @Test
    void testIsNextDownPositionOccupied_WhenCollisionDetected() {

        String[][] background = new String[rows][columns];
        background[2][0] = "#FFFFFF"; // Simula um bloco na célula diretamente abaixo
        when(mockArena.getBackground()).thenReturn(background);

        block.setPosition(new Position(0, 0));


        boolean result = block.isNextDownPositonOccupied(mockArena);


        assertTrue(result, "The next position below should be occupied.");
    }
    */

    @Test
    void testCanMoveLeft_WhenNotAtLeftEdgeAndNoCollision() {
        // Configurar uma arena sem colisões
        when(mockArena.getBackground()).thenReturn(new String[rows][columns]);
        block.setPosition(new Position(1, 0)); // Não na borda esquerda

        boolean result = block.canMoveLeft(mockArena);

        assertTrue(result, "The block should be able to move left when not at the left edge and no collision.");
    }

    @Test
    void testCanMoveLeft_WhenAtLeftEdge() {
        // Configurar a posição na borda esquerda
        block.setPosition(new Position(0, 0)); // Na borda esquerda

        boolean result = block.canMoveLeft(mockArena);

        assertFalse(result, "The block should not be able to move left when at the left edge.");
    }

    @Test
    void testCanMoveLeft_WhenCollisionOnTopLeft() {
        // Simular uma arena com uma colisão à esquerda do bloco
        String[][] background = new String[rows][columns];
        background[1][0] = "#FFFFFF"; // Simula um obstáculo imediatamente à esquerda
        when(mockArena.getBackground()).thenReturn(background);
        block.setPosition(new Position(1, 1)); // Próximo à colisão

        boolean result = block.canMoveLeft(mockArena);

        assertFalse(result, "The block should not be able to move left due to a collision on the left.");
    }

    @Test
    void testCanMoveLeft_WhenCollisionOnBottomLeft() {
        // Configurar uma arena onde há várias linhas do bloco sendo verificadas
        String[][] background = new String[rows][columns];
        background[2][0] = "#FFFFFF"; // Simula um obstáculo na linha inferior esquerda do bloco
        when(mockArena.getBackground()).thenReturn(background);
        block.setPosition(new Position(1, 1)); // Colocado acima do obstáculo

        boolean result = block.canMoveLeft(mockArena);

        assertFalse(result, "The block should not be able to move left due to a collision in one of its rows on the left.");
    }


    @Test
    void testCanMoveLeft_WhenNoCollisionButAtLeftEdge() {
        // Simular uma arena sem colisões, mas o bloco está na borda esquerda
        when(mockArena.getBackground()).thenReturn(new String[rows][columns]);
        block.setPosition(new Position(0, 0)); // Exatamente na borda esquerda

        boolean result = block.canMoveLeft(mockArena);

        assertFalse(result, "The block should not be able to move left because it is at the left edge.");
    }





    @Test
    void testCanMoveRight_WhenNotAtRightEdgeAndNoCollision() {
        // Configurar uma arena sem colisões
        when(mockArena.getBackground()).thenReturn(new String[rows][columns]);
        block.setPosition(new Position(columns - shape[0].length - 1, 1)); // Não na borda direita

        boolean result = block.canMoveRight(mockArena);

        assertTrue(result, "The block should be able to move right when not at the right edge and no collision.");
    }

    @Test
    void testCanMoveRight_WhenAtRightEdge() {
        // Configurar a posição na borda direita
        block.setPosition(new Position(columns - shape[0].length, 0)); // Na borda direita

        boolean result = block.canMoveRight(mockArena);

        assertFalse(result, "The block should not be able to move right when at the right edge.");
    }

    @Test
    void testCanMoveRight_WhenCollisionOnTopRight() {
        // Simular uma arena com uma colisão à direita do bloco
        String[][] background = new String[rows][columns];
        background[1][columns - 1] = "#FFFFFF"; // Simula um obstáculo imediatamente à direita
        when(mockArena.getBackground()).thenReturn(background);
        block.setPosition(new Position(columns - shape[0].length - 1, 1)); // Próximo à colisão //malfeito

        boolean result = block.canMoveRight(mockArena);

        assertFalse(result, "The block should not be able to move right due to a collision on the right.");
    }

    @Test
    void testCanMoveRight_WhenCollisionOnBottomRight() {
        // Configurar uma arena onde há várias linhas do bloco sendo verificadas
        String[][] background = new String[rows][columns];
        background[2][columns - 1] = "#FFFFFF"; // Simula um obstáculo na linha inferior direita do bloco
        when(mockArena.getBackground()).thenReturn(background);
        block.setPosition(new Position(columns - shape[0].length - 1, 1)); // Colocado acima do obstáculo

        boolean result = block.canMoveRight(mockArena);

        assertFalse(result, "The block should not be able to move right due to a collision in one of its rows on the right.");
    }

    @Test
    void testCanMoveRight_WhenNoCollisionButAtRightEdge() {
        // Simular uma arena sem colisões, mas o bloco está na borda direita
        when(mockArena.getBackground()).thenReturn(new String[rows][columns]);
        block.setPosition(new Position(columns - shape[0].length, 0)); // Exatamente na borda direita

        boolean result = block.canMoveRight(mockArena);

        assertFalse(result, "The block should not be able to move right because it is at the right edge.");
    }



}