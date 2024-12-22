package tetronix.View

import spock.lang.Specification
import tetronix.Game
import tetronix.Model.TetrisBlock
import tetronix.Model.Position
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize

class TetrisBlockViewTest extends Specification {

    def "Teste do método draw para TetrisBlock"() {
        given: "Um jogo mockado com um bloco de Tetris"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)
        def mockGameView = Mock(GameView)

        and: "Um bloco de Tetris com uma forma, cor, posição e rotação"
        Position position = new Position(5, 5)
        // Forma do bloco (um quadrado 2x2) usando int[][], não ArrayList
        def shape = [[1, 0],
                     [1, 0]] // Forma do bloco (um quadrado 2x2)
        def color = "blue"
        def rotation = 0 // Inicialmente sem rotação
        def columns = 10
        def rows = 20

        // Criação do bloco de Tetris com os parâmetros corretos
        def block = new TetrisBlock(shape,color,position,rotation,columns,rows)

        // Mocking
        mockGame.getTetris_block() >> block
        mockGame.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics

        and: "Inicializando a visualização do bloco de Tetris"
        def tetrisBlockView = new TetrisBlockView(mockGame, mockGameView)

        when: "O método draw é chamado"
        tetrisBlockView.draw()

        then: "A cor de fundo é definida para azul"
        1 * mockTextGraphics.setBackgroundColor(TextColor.Factory.fromString("blue"))

        and: "O método fillRectangle é chamado para desenhar cada célula do bloco na posição correta"
        // Posições baseadas no bloco (5,5)
        1 * mockTextGraphics.fillRectangle(new TerminalPosition(5, 5), new TerminalSize(2, 1), ' ') // Bloco na posição (5, 5)
        1 * mockTextGraphics.fillRectangle(new TerminalPosition(5, 6), new TerminalSize(2, 1), ' ') // Bloco na posição (5, 5)

        and: "Nenhuma chamada para preencher células que não fazem parte do bloco"
        0 * mockTextGraphics.fillRectangle(_, _, _) // Garantir que não há chamadas extras
    }
}
