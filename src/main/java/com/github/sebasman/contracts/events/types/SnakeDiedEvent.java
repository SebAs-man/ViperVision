package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;

/**
 * Event published when the snake dies
 */
public record SnakeDiedEvent() implements IGameEvent {
}
