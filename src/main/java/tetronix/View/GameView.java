package tetronix.View;


import com.googlecode.lanterna.graphics.TextGraphics;
import tetronix.Game;
import tetronix.Model.Bomb;
import tetronix.Model.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameView {
    private final ArenaView arenaView;
    private final TetrisBlockView tetrisBlockView;
    private final ScreenManager screenManager;
    private List<ElementViewer> elements = new ArrayList<>();
    private Game game;

    public GameView(Game game_) {
        this.arenaView = new ArenaView(game_);        // View da arena
        this.tetrisBlockView = new TetrisBlockView(game_); // View do bloco ativo
        this.screenManager = game_.getScreenManager();
        this.game = game_;

        elements.add(arenaView);
        elements.add(tetrisBlockView);
    }


    public void render() throws IOException {
        screenManager.clear();

        for(ElementViewer element : elements){
            element.draw();
        }

        // Render bombs
        for (Bomb bomb : game.getBombs()) {
            renderBomb(bomb);
            System.out.println("Rendering bomb at: Row " + bomb.getPosition().getRow_identifier() + ", Column " + bomb.getPosition().getColumn_identifier());
        }

        screenManager.refresh();
    }

    private void renderBomb(Bomb bomb) {
        Position pos = bomb.getPosition();
        TextGraphics textGraphics = screenManager.getTextGraphics();
        textGraphics.putString(pos.getColumn_identifier(), pos.getRow_identifier(), "*"); // Use 'B' to represent a bomb
    }
}
