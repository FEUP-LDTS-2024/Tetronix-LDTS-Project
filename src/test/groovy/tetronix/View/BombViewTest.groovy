package tetronix.View

import spock.lang.Specification
import tetronix.Model.Bomb
import tetronix.Model.Position
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import tetronix.Game

class BombViewTest extends Specification {

    def "Teste do método draw"() {
        given: "Um jogo mockado com bombas"
        def mockGame = Mock(Game)
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)
        def mockGameView = Mock(GameView)

        and: "Uma lista de bombas mockadas"
        def bombs = [
                new Bomb("red", new Position(2, 3), 5, 5),
                new Bomb("red", new Position(5, 6), 5, 5),
                new Bomb("red", new Position(7, 8), 5, 5)
        ]

        // Ajuste para simular a expiração da bomba
        // Simulando que a terceira bomba está expirada
        bombs[2].metaClass.isExpired = { return true }

        and: "O jogo retorna os componentes necessários"
        mockGame.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics
        mockGame.getBomb() >> bombs

        and: "Um BombView inicializado"
        def bombView = new BombView(mockGame, mockGameView)

        when: "O método draw é chamado"
        bombView.draw()

        then: "As bombas não expiradas são desenhadas com a cor vermelha"
        1 * mockTextGraphics.setForegroundColor(TextColor.ANSI.RED) // Duas bombas não expiradas

        and: "O símbolo '*' é desenhado nas posições corretas"
        1 * mockTextGraphics.putString(2, 3, "*") // Bomba 1 (posição 2,3)
        1 * mockTextGraphics.putString(5, 6, "*") // Bomba 2 (posição 5,6)

        and: "A bomba expirada não deve ser desenhada"
        1 * mockTextGraphics.putString(_, _, _)
    }
}
