package com.github.sebasman.contracts.model;

import com.github.sebasman.contracts.presenter.IControlStrategy;

/**
 * Defines the contract for a user profile.
 * Sets out the methods for accessing and modifying data that persist between line items.
 */
public interface IUserProfile {
    /**
     * Returns the current game state.
     * @return The current State object representing the game state.
     */
    IControlStrategy getLastPlayedStrategy();

    /**
     * Gets the high score of the user.
     * @return An integer that represents the high score.
     */
    int getHighScore();

    /**
     * Sets the last played strategy for the game.
     * @param lastPlayedStrategy The ControlStrategy instance representing the last played strategy.
     */
    void setLastPlayedStrategy(IControlStrategy lastPlayedStrategy);

    /**
     * Sets the high score the game
     * @param highScore the new high score
     */
    void setHighScore(int highScore);
}
