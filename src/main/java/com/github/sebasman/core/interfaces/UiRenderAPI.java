package com.github.sebasman.core.interfaces;

import com.github.sebasman.core.Game;

/**
 * Defines the contract for any classes that can render
 * the main game layout (frames, scores, etc.).
 */
public interface UiRenderAPI {
    /**
     * Initializes the UI render API with the game instance.
     * @param game The main instance of the game (the context).
     */
    void initialize(Game game);

    /**
     * Renders the UI elements of the game.
     * @param game The main instance of the game (the context).
     * @param interpolation The percentage of progress towards the next tick (0.0 to 1.0).
     */
    void render(Game game, Float interpolation);
}
