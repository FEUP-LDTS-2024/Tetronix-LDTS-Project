package tetronix.View

import spock.lang.Specification
import tetronix.Model.Menu
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.TextColor

class MenuStatisticsViewTest extends Specification {

    def "Teste do método draw para MenuStatisticsView"() {
        given: "Um menu mockado com scores"
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)

        def scores = [100, 200, 300] // Scores de exemplo
        def menu = Mock(Menu)

        // Configurando o comportamento dos mocks
        menu.getTrack_Scores() >> scores
        menu.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics

        and: "Inicializando a visualização do menu de estatísticas"
        def menuStatisticsView = new MenuStatisticsView(menu)

        when: "O método draw é chamado"
        menuStatisticsView.draw()

        then: "O fundo é preenchido com a cor preta"
        1 * mockTextGraphics.setBackgroundColor(TextColor.Factory.fromString("black"))
        1 * mockTextGraphics.fill(' ')

        and: "O título '========GAME SCORES========' é desenhado"
        1 * mockTextGraphics.setForegroundColor(TextColor.ANSI.WHITE)
        1 * mockTextGraphics.putString(5, 2, "========GAME SCORES========")

        and: "Cada score é desenhado corretamente"
        1 * mockTextGraphics.putString(10, 4, "Game 1: 100")
        1 * mockTextGraphics.putString(10, 5, "Game 2: 200")
        1 * mockTextGraphics.putString(10, 6, "Game 3: 300")

        and: "As instruções são desenhadas"
        1 * mockTextGraphics.putString(5, 8, "Press 'N' for a new game")
        1 * mockTextGraphics.putString(5, 9, "Press 'ESC' to exit")
    }
}
