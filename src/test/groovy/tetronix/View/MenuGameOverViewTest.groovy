package tetronix.View

import spock.lang.Specification
import tetronix.Model.Menu
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.TextColor

class MenuGameOverViewTest extends Specification {

    def "Teste do método draw para MenuGame_OverView"() {
        given: "Um menu mockado com opções e uma opção selecionada"
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)

        def menuOptions = ["Retry", "Quit"]
        def selectedOption = 1 // "Quit" está selecionada
        def menu = Mock(Menu)

        // Configurando o comportamento dos mocks
        menu.getOptionsGameOver() >> menuOptions
        menu.getSelectedOption() >> selectedOption
        menu.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics

        and: "Inicializando a visualização do menu de Game Over"
        def menuGameOverView = new MenuGame_OverView(menu)

        when: "O método draw é chamado"
        menuGameOverView.draw()

        then: "O título do Game Over é desenhado"
        1 * mockTextGraphics.setBackgroundColor(TextColor.Factory.fromString("black"))
        1 * mockTextGraphics.fill(' ')
        1 * mockTextGraphics.setForegroundColor(TextColor.ANSI.WHITE)
        1 * mockTextGraphics.putString(10, 2, "===== GAME OVER =====")

        and: "As opções do Game Over são desenhadas corretamente"
        1 * mockTextGraphics.setForegroundColor(TextColor.ANSI.WHITE)
        1 * mockTextGraphics.putString(10, 4, "  Retry")
        1 * mockTextGraphics.putString(10, 5, "> Quit")  // A opção selecionada ("Quit")

        and: "A cor da opção selecionada é amarela"
        1 * mockTextGraphics.setForegroundColor(TextColor.ANSI.YELLOW)

        and: "A arte do logo é desenhada nas posições corretas"
        1 * mockTextGraphics.setForegroundColor(TextColor.ANSI.CYAN)
        1 * mockTextGraphics.putString(2, 13, " ######      /\\    |\\    /| ########  ")
        1 * mockTextGraphics.putString(2, 14, " ##         /  \\   | \\  / | ##        ")
        1 * mockTextGraphics.putString(2, 15, " ##  ###   /----\\  |  \\/  | #######  ")
        1 * mockTextGraphics.putString(2, 16, " ##   ##  /      \\ |      | ##      ")
        1 * mockTextGraphics.putString(2, 17, " ######  /        \\|      | ########  ")
    }
}
