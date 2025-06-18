package com.github.sebasman.core.events.messages;

import com.github.sebasman.core.events.GameEvent;

/**
 * Event that is published when the snake eats and grows.
 */
public class SnakeGrewEvent implements GameEvent {
    public final int length;

    /**
     * Constructor of the message
     * @param length the new size of the snake
     */
    public SnakeGrewEvent(int length) {
        if(length < 0) throw new IllegalArgumentException("Length cannot be negative");
        this.length = length;
    }
}
