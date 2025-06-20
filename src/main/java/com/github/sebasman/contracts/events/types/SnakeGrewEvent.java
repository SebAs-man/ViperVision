package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;

/**
 *
 * @param length
 */
public record SnakeGrewEvent(int length) implements IGameEvent {
    /**
     * Constructor of the message
     */
    public SnakeGrewEvent {
        if (length < 0) throw new IllegalArgumentException("Length cannot be negative");
    }
}
