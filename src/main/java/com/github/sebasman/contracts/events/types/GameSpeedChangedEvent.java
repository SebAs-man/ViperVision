package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;

/**
 * Published when an effect requests a temporary change in the game's tick speed.
 * The PlayingState is the sole listener for this event.
 * @param speed A new value for the update tick rate.
 */
public record GameSpeedChangedEvent(float speed) implements IGameEvent {
}
