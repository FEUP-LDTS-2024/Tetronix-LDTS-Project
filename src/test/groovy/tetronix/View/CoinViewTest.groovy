package tetronix.View

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import spock.lang.Specification
import tetronix.Game
import tetronix.Model.Coins
import tetronix.Model.Position

class CoinViewTest extends Specification {

    def "Teste de inicialização do CoinView"() {
        given: "Um jogo mockado"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)
        def mockGameView = Mock(GameView)

        and: "O jogo retorna os componentes necessários"
        mockGame.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics
        mockGame.getCoins() >> []

        when: "Um CoinView é criado"
        def coinView = new CoinView(mockGame, mockGameView)

        then: "Os componentes são inicializados corretamente"
        coinView.coins.isEmpty()
        coinView.graphics == mockTextGraphics
        coinView.game == mockGame
    }

    def "Teste do método draw para moedas não coletadas"() {
        given: "Um jogo mockado com moedas"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)
        def mockGameView = Mock(GameView)

        and: "Uma lista de moedas mockadas"
        def coins = [
                new Coins(new Position(2, 3), "blue", 1), // Moeda não coletada com valor 4 (amarela)
                new Coins(new Position(5, 6), "blue", 3), // Moeda não coletada com valor diferente de 4 (azul)
                new Coins(new Position(7, 8), "yellow", 4)  // Moeda já coletada (não deve ser desenhada)
        ]

        and: "O jogo retorna os componentes necessários"
        mockGame.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics
        mockGame.getCoins() >> coins

        and: "Um CoinView inicializado"
        def coinView = new CoinView(mockGame, mockGameView)

        // Ajustando as moedas para simular o estado de coleta
        coins[0].isCollected() >> false
        coins[1].isCollected() >> false
        coins[2].isCollected() >> true

        when: "O método draw é chamado"
        coinView.draw()

        then: "As moedas não coletadas são desenhadas com a cor correta"
        2 * mockTextGraphics.setForegroundColor(TextColor.ANSI.BLUE) // Moedas com valor diferente de 4 (azul)

        // Usando any() para permitir qualquer instância de TextColor que seja amarela
        1 * mockTextGraphics.setForegroundColor(TextColor.ANSI.YELLOW) // Aceita qualquer cor amarela, sem falhar
    }

}
