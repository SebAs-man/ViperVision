package com.github.sebasman.core.events;

import com.github.sebasman.core.Game;

/**
 * Defines the contract for any object that wants to listen and react to game events.
 * Objects implementing this interface will be able to subscribe to the EventManager.
 */
@FunctionalInterface
public interface EventListener {
    /**
     * Method called by the EventManager when an event occurs to which this listener is subscribed.
     * @param eventType The type of event that occurred.
     * @param context The main instance of the game, providing access to the general state of the game.
     */
    void update(GameEvent eventType, Game context);
}
