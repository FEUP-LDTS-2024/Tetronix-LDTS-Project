GameThread:
    A classe game é a classe "principal" que executa as ações (como usar as teclas para mover / rodar os blocos).
    Mas nós precisamos que os blocos caiam infinitamente sem estarmos a precisar de clicar nas teclas.
    Esta classe serve estão para isso, é uma execução paralela à classe game que a cada segundo faz o bloco cair 1 posição.

    Para mudar a velocidade de cair do bloco, apenas mudar "Thread.sleep(125)";

Game:
    Há algum problema no facto da função "draw()" ser pública? Teve de ficar a pública para poder ser usada na classe GameThread.

    "moveBlockDown()": como é que a função sabe qual é o bloco a que me estou a referir se apenas é passado uma posição como argumento?


