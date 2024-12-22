package tetronix.Control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetronix.Game;

import static org.mockito.Mockito.*;

class GameThreadTest {

    private Game mockGame;
    private GameThread gameThread;

    @BeforeEach
    void setUp() {
        mockGame = mock(Game.class);

        // Configurar os valores iniciais esperados
        when(mockGame.getInitial_speed()).thenReturn(500);
        when(mockGame.getSpeed_per_level()).thenReturn(50);
        when(mockGame.getLevel()).thenReturn(1);
        gameThread = new GameThread(mockGame);
    }

    @Test
    void testRun_LevelUpLogic() throws InterruptedException {
        // Simula a possibilidade de subir de nível
        when(mockGame.can_level_up()).thenReturn(true, false); // Subir nível apenas uma vez
        when(mockGame.updateGameState()).thenReturn(true, true, false); // Estado do jogo: ativo, ativo, fim

        // Executa o run com um loop controlado
        Thread thread = new Thread(() -> gameThread.run());
        thread.start();

        // Aguarda um pouco para a execução
        Thread.sleep(1000);
        thread.interrupt(); // Interrompe a thread para evitar loop infinito

        // Verifica as chamadas feitas durante o loop
        verify(mockGame, times(1)).setLevel(2); // Verifica que o nível foi incrementado uma vez
        verify(mockGame, times(3)).updateGameState(); // Chama updateGameState 3 vezes
        verify(mockGame).setTetris_block(null); // Verifica que o bloco foi desativado no final
    }

    @Test
    void testRun_SpeedControl() throws InterruptedException {
        // Configurar o comportamento do game
        when(mockGame.can_level_up()).thenReturn(false); // Não sobe de nível
        when(mockGame.updateGameState()).thenReturn(true, true, false); // Fim do jogo após duas atualizações

        // Executa o run
        Thread thread = new Thread(() -> gameThread.run());
        thread.start();

        // Aguarda um pouco para a execução
        Thread.sleep(1000);
        thread.interrupt(); // Interrompe a thread

        // Verifica as chamadas de delay
        verify(mockGame, times(3)).updateGameState(); // Verifica que o estado foi atualizado 3 vezes
    }

    @Test
    void testRun_GameEnds() {
        when(mockGame.updateGameState()).thenReturn(false); // Fim do jogo imediatamente

        // Executa o run
        gameThread.run();

        // Verifica que o bloco foi desativado imediatamente
        verify(mockGame).setTetris_block(null);
        verify(mockGame, never()).setLevel(anyInt()); // Verifica que o nível não foi alterado
    }

    @Test
    void testRun_SpeedMinimum() throws InterruptedException {
        when(mockGame.getLevel()).thenReturn(10); // Define um nível alto para testar velocidade mínima
        when(mockGame.updateGameState()).thenReturn(true, false); // Uma iteração antes de terminar

        // Executa o run
        Thread thread = new Thread(() -> gameThread.run());
        thread.start();

        // Aguarda um pouco para a execução
        Thread.sleep(500);
        thread.interrupt();

        // Não podemos verificar diretamente a espera, mas garantimos que o jogo continuou uma vez
        verify(mockGame, times(2)).updateGameState();
    }
}
