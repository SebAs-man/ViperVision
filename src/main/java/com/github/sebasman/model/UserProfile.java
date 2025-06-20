package com.github.sebasman.model;

import com.github.sebasman.contracts.presenter.IControlStrategy;

import java.util.Objects;

/**
 * Contains user data that persists between game sessions.
 */
public final class UserProfile {
    // last strategy used by the user
    private IControlStrategy lastPlayedStrategy;
    // USER RECORD
    private int highScore;

    /**
     * Build a new user
     */
    public UserProfile() {
        this.highScore = 0;
    }

    // --- Getters ---

    /**
     * Returns the current game state.
     * @return The current State object representing the game state.
     */
    public IControlStrategy getLastPlayedStrategy() {
        return lastPlayedStrategy;
    }

    /**
     * Gets the high score of the user.
     * @return An integer that represents the high score.
     */
    public int getHighScore() {
        return highScore;
    }

    // --- Setters ---

    /**
     * Sets the last played strategy for the game.
     * @param lastPlayedStrategy The ControlStrategy instance representing the last played strategy.
     */
    public void setLastPlayedStrategy(IControlStrategy lastPlayedStrategy) {
        Objects.requireNonNull(lastPlayedStrategy, "Last played strategy cannot be null");
        this.lastPlayedStrategy = lastPlayedStrategy;
    }

    /**
     * Sets the high score the game
     * @param highScore the new high score
     */
    public void setHighScore(int highScore) {
        if(highScore < 0) throw new IllegalArgumentException("High score cannot be negative.");
        this.highScore = highScore;
    }
}
