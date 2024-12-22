package tetronix.View

import spock.lang.Specification
import tetronix.Model.TetrisBlock
import tetronix.Game
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize

class NextBlockViewTest extends Specification {

    def "Teste do método draw para o próximo bloco de Tetris"() {
        given: "Um jogo mockado com o próximo bloco de Tetris"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)
        def mockGameView = Mock(GameView)
        def mockNextBlock = Mock(TetrisBlock)

        and: "Configuração do próximo bloco de Tetris"
        mockGame.getNextBlock() >> mockNextBlock
        mockGame.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics

        // Definindo o mock do próximo bloco de Tetris
        def shape = [[1, 1], [1, 1]]  // Bloco 2x2
        def color = "blue"
        mockNextBlock.getShape() >> shape
        mockNextBlock.getColor() >> color

        and: "Inicializando a visualização do próximo bloco"
        def nextBlockView = new NextBlockView(mockGame, mockGameView)

        when: "O método draw é chamado"
        nextBlockView.draw()

        then: "O texto 'NEXT BLOCK' é desenhado na posição correta"
        1 * mockTextGraphics.putString(24, 8, "NEXT BLOCK")

        and: "A cor de fundo é definida corretamente para o bloco"
        1 * mockTextGraphics.setBackgroundColor(TextColor.Factory.fromString("blue"))

        and: "O bloco é desenhado na posição correta dentro do retângulo"
        1 * mockTextGraphics.fillRectangle(new TerminalPosition(24, 10), new TerminalSize(10, 6), ' ')

        and: "O bloco 2x2 é desenhado corretamente"
        4 * mockTextGraphics.fillRectangle(_, _, _)  // Espera-se 4 chamadas para desenhar o bloco 2x2
    }

    def "Teste do método draw quando não há próximo bloco"() {
        given: "Um jogo mockado sem próximo bloco de Tetris"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)
        def mockGameView = Mock(GameView)

        and: "O jogo retorna null para o próximo bloco"
        mockGame.getNextBlock() >> null
        mockGame.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics

        and: "Inicializando a visualização do próximo bloco"
        def nextBlockView = new NextBlockView(mockGame, mockGameView)

        when: "O método draw é chamado"
        nextBlockView.draw()

        then: "Nenhuma operação de desenho é realizada"
        0 * mockTextGraphics._
    }
}
