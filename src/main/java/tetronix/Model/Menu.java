package tetronix.Model;

public class Menu {
    private String[] opcoes ={"Start Game", "Options", "Exit"};
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
