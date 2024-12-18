package tetronix.Model

import spock.lang.Specification

class PositionSpec extends Specification {

    def "Deve criar um objeto Position com os valores corretos do construtor"() {
        when: "Criamos uma posição com valores específicos"
        def position = new Position(5, 10)

        then: "Os valores de coluna e linha estão corretos"
        position.getColumn_identifier() == 5
        position.getRow_identifier() == 10
    }

    def "Deve permitir alterar o valor de column_identifier usando setColumn_identifier"() {
        given: "Uma posição inicial"
        def position = new Position(3, 6)

        when: "Alteramos o valor da coluna"
        position.setColumn_identifier(8)

        then: "O novo valor da coluna é refletido"
        position.getColumn_identifier() == 8
    }

    def "Deve permitir alterar o valor de row_identifier usando setRow_identifier"() {
        given: "Uma posição inicial"
        def position = new Position(4, 2)

        when: "Alteramos o valor da linha"
        position.setRow_identifier(7)

        then: "O novo valor da linha é refletido"
        position.getRow_identifier() == 7
    }

    def "Deve manter valores corretos ao usar simultaneamente os setters"() {
        given: "Uma posição inicial"
        def position = new Position(1, 1)

        when: "Alteramos os valores de coluna e linha"
        position.setColumn_identifier(9)
        position.setRow_identifier(11)

        then: "Os valores são atualizados corretamente"
        position.getColumn_identifier() == 9
        position.getRow_identifier() == 11
    }
}
