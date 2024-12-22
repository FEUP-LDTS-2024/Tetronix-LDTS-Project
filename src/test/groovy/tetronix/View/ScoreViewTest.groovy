package tetronix.View

import spock.lang.Specification
import tetronix.Game
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.TextColor

class ScoreViewTest extends Specification {

    def "Teste do método draw para exibir o nível e a pontuação"() {
        given: "Um jogo mockado"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)
        def mockGameView = Mock(GameView)

        and: "O jogo retorna um nível, pontuação e pontos adicionais"
        mockGame.getLevel() >> 5
        mockGame.getScore() >> 100
        mockGame.get_Additional_Points() >> 20
        mockGame.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics

        and: "Inicializando a visualização do placar"
        def scoreView = new ScoreView(mockGame, mockGameView)

        when: "O método draw é chamado"
        scoreView.draw()

        then: "A cor do texto é definida para branco"
        1 * mockTextGraphics.setForegroundColor(TextColor.Factory.fromString("white"))

        and: "O texto do nível é desenhado na posição correta"
        1 * mockTextGraphics.putString(25, 4, "Level: 5")

        and: "O texto da pontuação é desenhado na posição correta"
        1 * mockTextGraphics.putString(25, 6, "Score: 120") // 100 + 20 (pontuação + pontos adicionais)
    }

    def "Teste do método draw quando não há pontos adicionais"() {
        given: "Um jogo mockado sem pontos adicionais"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)
        def mockGameView = Mock(GameView)

        and: "O jogo retorna um nível, pontuação, mas sem pontos adicionais"
        mockGame.getLevel() >> 5
        mockGame.getScore() >> 100
        mockGame.get_Additional_Points() >> 0
        mockGame.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics

        and: "Inicializando a visualização do placar"
        def scoreView = new ScoreView(mockGame, mockGameView)

        when: "O método draw é chamado"
        scoreView.draw()

        then: "A cor do texto é definida para branco"
        1 * mockTextGraphics.setForegroundColor(TextColor.Factory.fromString("white"))

        and: "O texto do nível é desenhado na posição correta"
        1 * mockTextGraphics.putString(25, 4, "Level: 5")

        and: "O texto da pontuação é desenhado na posição correta"
        1 * mockTextGraphics.putString(25, 6, "Score: 100") // 100 pontos, sem pontos adicionais
    }
}
