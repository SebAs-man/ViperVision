package com.github.sebasman.strategies;


import com.github.sebasman.core.Game;
import com.github.sebasman.entities.Snake;

/**
 * Interface for the Strategy pattern. Defines a contract for all snake control strategies (human, AI, etc.).
 */
public interface ControlStrategy {
    /**
     * Called on every frame of the game loop.
     * Ideal for AIs that need to recalculate their movement constantly.
     * @param game The game instance to access global data.
     * @param snake The snake that this strategy should control.
     */
    void update(Game game, Snake snake);


    /**
     * Called each time a key is pressed.
     * Ideal for human control, which is event driven.
     * @param game The game instance.
     * @param snake The snake to control.
     * @param keyCode The code of the key pressed.
     */
    void keyPressed(Game game, Snake snake, int keyCode);
}
