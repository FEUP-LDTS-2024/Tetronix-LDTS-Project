package tetronix

import spock.lang.Specification
import tetronix.Model.Coins
import tetronix.Model.Position

class CoinsSpec extends Specification {

    def "Deve inicializar Coins corretamente com posição, cor e valor"() {
        given: "Uma posição, cor e valor específicos"
        def position = new Position(5, 10)
        def color = "yellow"
        def value = 100

        when: "Um objeto Coins é criado"
        def coin = new Coins(position, color, value)

        then: "Os valores iniciais devem ser definidos corretamente"
        coin.getPosition() == position
        coin.getColor() == color
        coin.getValue() == value
        !coin.isCollected()
    }

    def "Deve retornar o valor correto com getValue()"() {
        given: "Uma moeda com um valor específico"
        def coin = new Coins(new Position(0, 0), "red", 200)

        expect: "O valor retornado deve ser o esperado"
        coin.getValue() == 200
    }

    def "Deve retornar false para isCollected() inicialmente"() {
        given: "Uma moeda recém-criada"
        def coin = new Coins(new Position(0, 0), "green", 50)

        expect: "isCollected() deve retornar false"
        !coin.isCollected()
    }

    def "Deve alterar o estado de collected para true ao chamar collect()"() {
        given: "Uma moeda não coletada"
        def coin = new Coins(new Position(0, 0), "blue", 75)

        when: "O método collect() é chamado"
        coin.collect()

        then: "O estado collected deve ser true"
        coin.isCollected()
    }

    def "Chamar collect() mais de uma vez não deve alterar o estado"() {
        given: "Uma moeda coletada"
        def coin = new Coins(new Position(0, 0), "purple", 30)

        when: "O método collect() é chamado duas vezes"
        coin.collect()
        coin.collect()

        then: "O estado collected deve permanecer true"
        coin.isCollected()
    }
}
