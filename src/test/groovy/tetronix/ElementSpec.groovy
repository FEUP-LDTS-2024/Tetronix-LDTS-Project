package tetronix.Model

import spock.lang.Specification

class ElementSpec extends Specification {

    def "Deve testar os getters e setters da classe Element"() {
        given: "Uma classe concreta que estende a classe abstrata Element"

        and: "Uma instância da classe concreta com valores iniciais"
        def initialPosition = new Position(2, 3)
        def initialColor = "red"
        def element = new TestElement(initialPosition, initialColor)

        expect: "Os getters retornam os valores iniciais corretamente"
        element.getPosition() == initialPosition
        element.getColor() == initialColor

        when: "O setter da posição é chamado"
        def newPosition = new Position(5, 6)
        element.setPosition(newPosition)

        then: "O getter da posição deve retornar o novo valor"
        element.getPosition() == newPosition
    }

    class TestElement extends Element {
        TestElement(Position position, String color) {
            super(position, color)
        }
    }
}
