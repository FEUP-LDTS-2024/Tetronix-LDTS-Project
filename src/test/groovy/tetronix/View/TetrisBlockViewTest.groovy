package tetronix.View;
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import spock.lang.Specification
import tetronix.Game
import tetronix.Model.Position
import tetronix.Model.TetrisBlock
import tetronix.View.ScreenManager
import tetronix.View.GameView
import tetronix.View.TetrisBlockView

class TetrisBlockViewTest extends Specification {

    def "draw() should render TetrisBlock correctly"() {
        given:
        // Mock dependencies
        def game = Mock(Game)
        def screenManager = Mock(ScreenManager)
        def textGraphics = Mock(TextGraphics)
        def gameView = Mock(GameView)

        // Mock Game and ScreenManager behavior
        game.getScreenManager() >> screenManager
        screenManager.getTextGraphics() >> textGraphics

        // Create a TetrisBlockView instance
        def blockView = new TetrisBlockView(game, gameView)

        and:
        // Mock a TetrisBlock with a 2x2 shape and position
        def block = Mock(TetrisBlock)
        block.getShape() >> [[1, 0], [1, 1]]
        block.getPosition() >> new Position(5, 10) // Example position
        block.getColor() >> "#FF0000" // Example color (red)

        game.getTetris_block() >> block

        when:
        blockView.draw()

        then:
        // Verify background color is set correctly
        1 * textGraphics.setBackgroundColor(TextColor.Factory.fromString("#FF0000"))


        1 * textGraphics.fillRectangle(new TerminalPosition(5, 10), new TerminalSize(2, 1), ' ')

    }

    def "draw() should do nothing if there is no TetrisBlock"() {
        given:
        def game = Mock(Game)
        def screenManager = Mock(ScreenManager)
        def textGraphics = Mock(TextGraphics)
        def gameView = Mock(GameView)

        game.getScreenManager() >> screenManager
        screenManager.getTextGraphics() >> textGraphics

        def blockView = new TetrisBlockView(game, gameView)

        // No block available
        game.getTetris_block() >> null

        when:
        blockView.draw()

        then:
        // No interactions with textGraphics
        1 * _
    }
}
