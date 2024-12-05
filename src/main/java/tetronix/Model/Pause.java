package tetronix.Model;

public class Pause {
    private String[] opcoes ={"Continue", "New Game", "Main Menu"};
    private int selecao=0;

    public String[]getOpcoes(){
        return opcoes;
    }
    public int getSelecao (){
        return selecao;
    }
    public void proximaOpcao(){
        selecao=(selecao+1)%opcoes.length;
    }
    public void voltarOpcao(){
        selecao=(selecao-1+opcoes.length)% opcoes.length;
    }


}
