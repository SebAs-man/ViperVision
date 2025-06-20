package com.github.sebasman.contracts.view;

import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.model.GameSession;
import com.github.sebasman.model.UserProfile;

import processing.core.PFont;

/**
 * Defines the contract for the main Game View.
 * Exposes the methods that the Presenters (IState) can call to interact
 * with the application state and to perform PApplet drawing operations,
 * without coupling to the concrete GameView implementation.
 */
public interface IGameView {
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
    GameSession getSession();

    /**
     * Gets a current profile in the game.
     * @return An instance of the current profile.
     */
    UserProfile getProfile();

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
}
