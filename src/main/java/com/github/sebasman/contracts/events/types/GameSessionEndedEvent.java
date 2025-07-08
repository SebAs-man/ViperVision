package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;

/**
 * Posted when a game session ends
 * (e.g., when returning to the main menu from GameOver or Pause).
 */
public record GameSessionEndedEvent() implements IGameEvent {
}
