package com.github.sebasman.contracts.view;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.IUserProfile;
import com.github.sebasman.contracts.presenter.IState;
import processing.core.PApplet;


/**
 * Defines the contract for the main context of the application.
 * Provides the Presenters (States) with controlled access to the global state
 * and to the application functionalities without coupling them to the
 * concrete implementation of the View (GameView).
 */
public interface IGameContext {
    // --- Application state management methods ---

    /**
     * Start a new game session
     */
    void startNewSession();

    /**
     * End of the current game session
     */
    void endCurrentSession();

    /**
     * Gets a current game session.
     * @return An instance of the current game session or null.
     */
    IGameSession getSession();

    /**
     * Gets a current profile in the game.
     * @return An instance of the current profile.
     */
    IUserProfile getProfile();

    // --- State stack management (flow control) methods ---

    /**
     * Returns the current game state without removing it from the stack.
     * @return The current state of the game, or null if the stack is empty.
     */
    IState peekState();

    /**
     * Pushes a new game state onto the stack and notifies it.
     * @param state The new state to be pushed onto the stack.
     */
    void pushState(IState state);

    /**
     * Changes the current game state to a new state.
     * @param newState The new state to switch to.
     */
    void changeState(IState newState);

    /**
     * Removes the current state from the stack.
     */
    void popState();

    /**
     * Provides access to the PApplet rendering engine so that.
     * View components and presenters to draw.
     * @return The PApplet instance.
     */
    PApplet getRenderer();
}
