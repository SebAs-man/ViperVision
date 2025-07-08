package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;
import com.github.sebasman.contracts.vo.Position;

import java.util.List;

public record AiPathUpdatedEvent(List<Position> path) implements IGameEvent {
}
