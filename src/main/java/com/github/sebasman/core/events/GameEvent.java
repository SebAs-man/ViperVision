package com.github.sebasman.core.events;

/**
 * A marker interface representing an in-game event.
 * All concrete event classes must implement this interface.
 * Serves as an upper bound for generic types in the EventManager,
 * Ensuring type safety throughout the event system.
 */
public interface GameEvent {
}
