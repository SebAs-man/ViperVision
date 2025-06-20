package com.github.sebasman.contracts.presenter;

import com.github.sebasman.contracts.model.ISnakeAPI;
import com.github.sebasman.view.GameView;

/**
 * Interface for the Strategy pattern. Defines a contract for all snake control strategies (human, AI, etc.).
 */
public interface IControlStrategy {
    /**
     * Called on every frame of the game loop.
     * Ideal for AIs that need to recalculate their movement constantly.
     * @param game The game instance to access global data.
     * @param snake The snake that this strategy should control.
     */
    void update(GameView game, ISnakeAPI snake);

    /**
     * Called each time a key is pressed.
     * Ideal for human control, which is event driven.
     * @param game The game instance.
     * @param snake The snake to control.
     * @param keyCode The code of the key pressed.
     */
    void keyPressed(GameView game, ISnakeAPI snake, int keyCode);

    /**
     * Determines whether a keystroke is to be interpreted as an action.
     * To start the game from a ready state.
     * @param keyCode The key code.
     * @return true if the key should start the game, false otherwise.
     */
    boolean isGameStartAction(int keyCode);
}
