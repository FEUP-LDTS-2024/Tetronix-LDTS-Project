import spock.lang.Specification
import tetronix.View.GameOverView
import tetronix.View.ScreenManager
import tetronix.Game
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize

class GameOverViewTest extends Specification {

    def "Teste do método draw para GameOverView"() {
        given: "Um jogo mockado e um screen manager mockado"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)

        // Configurando o comportamento do mock
        mockGame.get_Additional_Points() >> 123 // Pontuação de exemplo
        mockScreenManager.getTextGraphics() >> mockTextGraphics

        and: "Inicializando a visualização da tela de Game Over"
        def gameOverView = new GameOverView(mockScreenManager, mockGame)

        when: "O método draw é chamado"
        gameOverView.draw()

        then: "O fundo da tela é preenchido com a cor preta"
        1 * mockTextGraphics.setBackgroundColor(TextColor.Factory.fromString("black"))
        1 * mockTextGraphics.fillRectangle(new TerminalPosition(2, 5), new TerminalSize(30, 10), ' ')

        and: "O título '========GAME OVER========' é desenhado em negrito"
        1 * mockTextGraphics.enableModifiers(SGR.BOLD)
        1 * mockTextGraphics.putString(5, 6, "========GAME OVER========")

        and: "A pontuação é exibida com a cor vermelha"
        1 * mockTextGraphics.setForegroundColor(TextColor.ANSI.RED)
        1 * mockTextGraphics.putString(3, 10, "YOUR SCORE: 123")

        and: "A mensagem 'PRESS ANY KEY TO CONTINUE' é exibida"
        1 * mockTextGraphics.putString(3, 11, "PRESS ANY KEY TO CONTINUE")
    }
}
