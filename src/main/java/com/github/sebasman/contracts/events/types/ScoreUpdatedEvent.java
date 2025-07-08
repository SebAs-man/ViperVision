package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;

/**
 * Event published when the current or maximum score of the game is changed.
 * @param score The current game score
 * @param highScore The highest score achieved in the game
 */
public record ScoreUpdatedEvent(int score, int highScore) implements IGameEvent {
    /**
     * Constructor of the type event
     * @param score the new score
     * @param highScore the new high score
     */
    public ScoreUpdatedEvent {
        if (score < 0 || highScore < 0) throw new IllegalArgumentException("Score/High-score cannot be negative");
    }
}
