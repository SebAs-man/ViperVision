package com.github.sebasman.view.render;

import com.github.sebasman.model.GameSession;
import com.github.sebasman.view.GameView;
import com.github.sebasman.model.Board;
import com.github.sebasman.GameConfig;

/**
 * Singleton Renderer responsible EXCLUSIVELY for drawing the entities
 * of the game world (board, snake, food).
 * It is a class of the ‘View’ layer and is “dumb”: it only draws what is passed to it.
 */
public final class GameWorldRenderer {
    // Singleton instance for the GameWorldRenderer class
    private static final GameWorldRenderer INSTANCE = new GameWorldRenderer();

    /**
     * Private constructor to prevent instantiation.
     */
    private GameWorldRenderer() {}

    /**
     * Returns the singleton instance of GameWorldRenderer.
     * @return The singleton instance of GameWorldRenderer.
     */
    public static GameWorldRenderer getInstance() {
        return INSTANCE;
    }

    /**
     * Draws the current state of the game world.
     * @param game The PApplet context for drawing operations.
     * @param interpolation The factor for smooth snake movement.
     */
    public void render(GameView game, Float interpolation) {
        GameSession session = game.getSession();
        // Draw the game board, snake, and food
        game.pushMatrix();
        game.translate(GameConfig.GAME_AREA_PADDING, GameConfig.GAME_AREA_PADDING*2 + GameConfig.TOP_BAR_HEIGHT);
        Board.getInstance().draw(game, null);
        if(session != null){
            session.getFood().draw(game, null);
            session.getSnake().draw(game, interpolation);
        }
        game.popMatrix();
    }
}
