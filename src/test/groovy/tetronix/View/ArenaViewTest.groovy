package tetronix.View
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import spock.lang.Specification
import tetronix.Model.Arena
import tetronix.Game

class ArenaViewTest extends Specification {
    def "Teste do método draw quando todas as células do background são nulas"() {
        given: "Um mock de Arena com um background nulo"
        def mockGame = Mock(Game)
        def mockArena = Mock(Arena)
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)

        mockGame.getArena() >> mockArena
        mockGame.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics

        and: "Uma arena com um background de 3x3 contendo apenas valores nulos"
        def rows = 3
        def columns = 3
        def background = new String[rows][columns]
        mockArena.getRows() >> rows
        mockArena.getColumns() >> columns
        mockArena.getBackground() >> background

        and: "Um ArenaView inicializado"
        def arenaView = new ArenaView(mockGame, Mock(GameView))

        when: "O método draw é chamado"
        arenaView.draw()

        then: "O método preenche todas as células do fundo com a cor branca"
        rows * columns * mockTextGraphics.setBackgroundColor(TextColor.Factory.fromString("white"))
        rows * columns * mockTextGraphics.fillRectangle(_, _, ' ')
    }

    def "Teste do método draw quando o background contém cores específicas"() {
        given: "Um mock de Arena com um background colorido"
        def mockGame = Mock(Game)
        def mockArena = Mock(Arena)
        def mockScreenManager = Mock(ScreenManager)
        def mockTextGraphics = Mock(TextGraphics)

        mockGame.getArena() >> mockArena
        mockGame.getScreenManager() >> mockScreenManager
        mockScreenManager.getTextGraphics() >> mockTextGraphics

        and: "Uma arena com um background de 2x2 contendo cores específicas"
        def rows = 2
        def columns = 2
        def background = [
                ["red", "blue"],
                ["green", "yellow"]
        ] as String[][]
        mockArena.getRows() >> rows
        mockArena.getColumns() >> columns
        mockArena.getBackground() >> background

        and: "Um ArenaView inicializado"
        def arenaView = new ArenaView(mockGame, Mock(GameView))

        when: "O método draw é chamado"
        arenaView.draw()

        then: "Cada célula é desenhada com a cor correspondente"
        1 * mockTextGraphics.setBackgroundColor(TextColor.Factory.fromString("red"))
        1 * mockTextGraphics.setBackgroundColor(TextColor.Factory.fromString("blue"))
        1 * mockTextGraphics.setBackgroundColor(TextColor.Factory.fromString("green"))
        1 * mockTextGraphics.setBackgroundColor(TextColor.Factory.fromString("yellow"))
        rows * columns * mockTextGraphics.fillRectangle(_, _, ' ')
    }
}
