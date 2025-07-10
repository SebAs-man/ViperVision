package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;

/**
 * Published ONLY when a new high score has been achieved.
 * @param highScore The new high score value.
 */
public record NewHighScoreEvent(int highScore) implements IGameEvent {
}
