package tetronix.View

import spock.lang.Specification
import tetronix.Game
import tetronix.Model.Arena

class GameViewTest extends Specification {

    def "Teste de inicialização do GameView"() {
        given: "Um jogo mockado"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)

        and: "O jogo retorna um ScreenManager mockado"
        mockGame.getScreenManager() >> mockScreenManager

        when: "Um GameView é criado"
        def gameView = new GameView(mockGame)

        then: "Todos os componentes do GameView são inicializados corretamente"
        gameView.arenaView != null
        gameView.tetrisBlockView != null
        gameView.coinView != null
        gameView.bombView != null
        gameView.scoreView != null
        gameView.nextBlockView != null
        gameView.screenManager == mockScreenManager
    }

    def "Teste de adição de elementos ao GameView"() {
        given: "Um GameView inicializado"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)
        mockGame.getScreenManager() >> mockScreenManager
        def gameView = new GameView(mockGame)

        and: "Um elemento mockado para adicionar"
        def mockElement = Mock(ElementViewer)

        when: "O elemento é adicionado ao GameView"
        gameView.addElementToList(mockElement)

        then: "O elemento está presente na lista de elementos"
        gameView.elements.contains(mockElement)

        and: "A lista de elementos tem o tamanho esperado"
        gameView.elements.size() == 7 // Verifique o número correto de elementos
    }


    def "Teste do método render no GameView"() {
        given: "Um GameView com mocks"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)
        def mockArena = Mock(Arena)
        mockGame.getScreenManager() >> mockScreenManager
        mockGame.getArena() >> mockArena

        // Criação do GameView
        def gameView = new GameView(mockGame)
        gameView.removeAllElements();

        and: "Elementos mockados adicionados manualmente"
        def mockElement1 = Mock(ElementViewer)
        def mockElement2 = Mock(ElementViewer)
        gameView.addElementToList(mockElement1)
        gameView.addElementToList(mockElement2)


        when: "O método render é chamado"
        gameView.render()

        then: "O ScreenManager é limpo antes do render"
        1 * mockScreenManager.clear()

        and: "Os outros elementos na lista são desenhados"
        1 * mockElement1.draw()
        1 * mockElement2.draw()

        and: "O ScreenManager é atualizado após o render"
        1 * mockScreenManager.refresh()
    }

}
