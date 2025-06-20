package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;

/**
 *
 * @param score
 * @param highScore
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
