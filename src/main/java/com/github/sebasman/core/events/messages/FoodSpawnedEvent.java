package com.github.sebasman.core.events.messages;

import com.github.sebasman.core.events.GameEvent;
import com.github.sebasman.core.vo.Position;

/**
 * Event that is published when the food appears in a new position.
 */
public class FoodSpawnedEvent implements GameEvent {
    public final Position position;

    /**
     * Constructor of the message
     * @param position the new position of the Food
     */
    public FoodSpawnedEvent(Position position) {
        this.position = position;
    }
}
