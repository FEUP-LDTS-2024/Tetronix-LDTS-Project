package tetronix.View

import spock.lang.Specification
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory

class ScreenManagerTest extends Specification {

    def "Teste do construtor e dos métodos get do ScreenManager"() {
        given: "Uma configuração de terminal com 20 linhas e 50 colunas"
        def terminalMock = Mock(Terminal)
        def screenMock = Mock(Screen)
        def terminalFactory = new DefaultTerminalFactory()

        // Mocking a criação do terminal e tela
        terminalFactory.createTerminal() >> terminalMock


        and: "Instanciando o ScreenManager"
        def screenManager = new ScreenManager(20, 50)

        expect: "O ScreenManager foi inicializado com as dimensões corretas"
        screenManager.getColumns() == 50
        screenManager.getRows() == 20
    }
}
