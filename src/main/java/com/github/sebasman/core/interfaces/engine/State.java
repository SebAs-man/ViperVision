package com.github.sebasman.core.interfaces.engine;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.ui.UiProvider;
import com.github.sebasman.core.interfaces.ui.UiComponent;

import java.util.Collections;
import java.util.List;

/**
 * Interface that defines the behavior of a game state.
 * Each particular state will implement this interface to handle its own
 * update, rendering, and user input logic.
 */
public interface State {
    /**
     * Called once when this state is entered.
     * Ideal for state-specific initializations.
     * @param game The main instance of the game (the context).
     */
    void onEnter(Game game);

    /**
     * Updates the state logic at each frame.
     * @param game The main instance of the game (the context).
     */
    void update(Game game);

    /**
     * Draws the state elements on the screen.
     * @param game The main instance of the game (the context).
     * @param interpolation The percentage of progress towards the next tick (0.0 to 1.0).
     */
    void draw(Game game, Float interpolation);

    /**
     * Handles keyboard input specific to this state.
     * @param game The main instance of the game (the context).
     * @param keyCode The code of the key pressed.
     */
    void keyPressed(Game game, int keyCode);

    /**
     * Handles mouse press events specific to this state.
     * @param game The main instance of the game (the context).
     */
    void mousePressed(Game game);
}
