package com.github.sebasman.model;

import com.github.sebasman.contracts.model.IUserProfile;
import com.github.sebasman.contracts.presenter.IControlStrategy;

import java.util.Objects;

/**
 * Contains user data that persists between game sessions.
 */
public final class UserProfile implements IUserProfile {
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

    @Override
    public IControlStrategy getLastPlayedStrategy() {
        return lastPlayedStrategy;
    }

    @Override
    public int getHighScore() {
        return highScore;
    }

    @Override
    public void setLastPlayedStrategy(IControlStrategy lastPlayedStrategy) {
        Objects.requireNonNull(lastPlayedStrategy, "Last played strategy cannot be null");
        this.lastPlayedStrategy = lastPlayedStrategy;
    }

    @Override
    public void setHighScore(int highScore) {
        if(highScore < 0) throw new IllegalArgumentException("High score cannot be negative.");
        this.highScore = highScore;
    }
}
