package com.github.sebasman.core.events.messages;

import com.github.sebasman.core.events.GameEvent;

/**
 * Event that is published when the player's score changes.
 */
public class ScoreChangedEvent implements GameEvent {
    public final int score;

    /**
     * Constructor of the message
     * @param score the new score
     */
    public ScoreChangedEvent(int score) {
        if(score < 0) throw new IllegalArgumentException("Score cannot be negative");
        this.score = score;
    }
}
