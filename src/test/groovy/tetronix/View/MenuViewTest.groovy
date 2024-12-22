package tetronix.View

import spock.lang.Specification
import tetronix.Model.Menu
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.TextColor

class MenuViewTest extends Specification {

    def "Teste do método draw para MenuView"() {
        given: "Um menu mockado com opções e uma opção selecionada"
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)

        def menuOptions = ["Start Game", "Options", "Exit"]
        def selectedOption = 1 // "Options" está selecionada
        def menu = Mock(Menu)

        // Configurando o comportamento dos mocks
        menu.getOptions() >> menuOptions
        menu.getSelectedOption() >> selectedOption
        menu.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics

        and: "Inicializando a visualização do menu"
        def menuView = new MenuView(menu)

        when: "O método draw é chamado"
        menuView.draw()

        then: "O título do menu é desenhado"
        1 * mockTextGraphics.putString(2, 2, "============= MAIN MENU =============")

        and: "As opções do menu são desenhadas corretamente"
        3 * mockTextGraphics.setForegroundColor(TextColor.ANSI.WHITE)
        1 * mockTextGraphics.putString(10, 6, "  Start Game")
        1 * mockTextGraphics.putString(10, 7, "> Options")  // A opção selecionada ("Options")
        1 * mockTextGraphics.putString(10, 8, "  Exit")

        and: "A cor da opção selecionada é amarela"
        1 * mockTextGraphics.setForegroundColor(TextColor.ANSI.YELLOW)

        and: "A arte do logo é desenhada nas posições corretas"
        1 * mockTextGraphics.setForegroundColor(TextColor.ANSI.CYAN)
        1 * mockTextGraphics.putString(0, 13, " ### #### ### ###    #   #  # ### #   #")
        1 * mockTextGraphics.putString(0, 14, "  #  #     #  #  # #  #  ## #  #   # #")
        1 * mockTextGraphics.putString(0, 15, "  #  ###   #  ###  #   # ####  #    #")
        1 * mockTextGraphics.putString(0, 16, "  #  #     #  # #  #   # # ##  #   # #")
        1 * mockTextGraphics.putString(0, 17, "  #  ####  #  #  #  ##   #  # ### #   #")
    }
}
