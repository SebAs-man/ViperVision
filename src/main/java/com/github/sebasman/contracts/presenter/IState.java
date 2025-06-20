package com.github.sebasman.contracts.presenter;

import com.github.sebasman.view.GameView;

/**
 * Interface that defines the behavior of a game state.
 * Each particular state will implement this interface to handle its own
 * update, rendering, and user input logic.
 */
public interface IState {
    /**
     * Called once when this state is entered.
     * Ideal for state-specific initializations.
     * @param game The main instance of the game (the context).
     */
    void onEnter(GameView game);

    /**
     * Called once just before this state is exited (popped from the stack).
     * Ideal for cleanup, such as unsubscribing from events, to prevent memory leaks.
     * @param game The main instance of the game (the context).
     */
    void onExit(GameView game);

    /**
     * Updates the state logic at each frame.
     * @param game The main instance of the game (the context).
     */
    void update(GameView game);

    /**
     * Updates the game state at each tick.
     * @param game The main instance of the game (the context).
     */
    void gameTickUpdate(GameView game);

    /**
     * Draws the state elements on the screen.
     * @param game The main instance of the game (the context).
     * @param interpolation The percentage of progress towards the next tick (0.0 to 1.0).
     */
    void draw(GameView game, Float interpolation);

    /**
     * Handles keyboard input specific to this state.
     * @param game The main instance of the game (the context).
     * @param keyCode The code of the key pressed.
     */
    void keyPressed(GameView game, int keyCode);

    /**
     * Handles mouse pressed events specific to this state.
     * @param mouseX The x-coordinate of the mouse when pressed.
     * @param mouseY The y-coordinate of the mouse when pressed.
     */
    void mousePressed(int mouseX, int mouseY);
}
