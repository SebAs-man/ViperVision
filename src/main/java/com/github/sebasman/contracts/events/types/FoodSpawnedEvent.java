package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;
import com.github.sebasman.contracts.vo.Position;

/**
 *
 * @param position
 */
public record FoodSpawnedEvent(Position position) implements IGameEvent {
}
