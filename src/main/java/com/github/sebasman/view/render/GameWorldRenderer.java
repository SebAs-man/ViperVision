package com.github.sebasman.view.render;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.GameSessionEndedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.view.config.ViewConfig;
import processing.core.PApplet;

/**
 * Singleton Renderer responsible EXCLUSIVELY for drawing the entities
 * of the game world (board, snake, food).
 * It is a class of the ‘View’ layer and is “dumb”: it only draws what is passed to it.
 */
public final class GameWorldRenderer {
    // Singleton instance for the GameWorldRenderer class
    private static final GameWorldRenderer INSTANCE = new GameWorldRenderer();
    private float interpolation;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameWorldRenderer() {
        this.reboot();
        EventManager.getInstance().subscribe(GameSessionEndedEvent.class, _ -> reboot());
    }

    /**
     * Restart interpolation
     */
    private void reboot(){
        this.interpolation = 0.0f;
    }

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
    public void render(IGameContext game, Float interpolation) {
        if(interpolation != null) {
            this.interpolation = interpolation;
        }
        PApplet renderer = game.getRenderer();
        IGameSession session = game.getSession();
        // Draw the game board, snake, and food
        BoardRender.getInstance().draw(renderer);
        renderer.pushMatrix();
        if(session != null){
            ObstacleRenderer.getInstance().draw(renderer, session.getBoard());
            renderer.translate(ViewConfig.GAME_AREA_PADDING, ViewConfig.GAME_AREA_PADDING*2 + ViewConfig.TOP_BAR_HEIGHT);
            PathRenderer.getInstance().draw(renderer);
            SnakeRender.getInstance().draw(renderer, this.interpolation, session.getSnake());
            FoodRender.getInstance().draw(renderer, session.getFood());
        }
        renderer.popMatrix();
    }
}
