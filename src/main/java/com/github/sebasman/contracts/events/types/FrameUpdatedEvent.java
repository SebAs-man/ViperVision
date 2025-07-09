package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;

/**
 * An event published on every single frame of the game's main loop.
 * This serves as a "heartbeat" for any system that needs to perform
 * per-frame logic, such as timers or animation managers, in a decoupled way.
 */
public record FrameUpdatedEvent() implements IGameEvent {
}
