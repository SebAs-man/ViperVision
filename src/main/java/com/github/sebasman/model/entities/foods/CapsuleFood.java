package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.vo.NotificationType;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

import java.util.Random;

/**
 * Represents a growth capsule food in the game that can be consumed,
 * but the snake should be careful
 */
public class CapsuleFood extends Food {
    private static final int MIN_RANGE = 5;
    private static final int MAX_RANGE = 10;

    /**
     * Creates an instance of the capsule.
     * @param position The initial position of this capsule.
     */
    public CapsuleFood(Position position) {
        super(0, position, 1);
    }

    @Override
    public void applyEffect(IGameSession session) {
        EventManager.getInstance().notify(new NotificationRequestedEvent(
                "growth capsule consumed!", NotificationType.WARNING, 1000
        ));
    }

    @Override
    public PImage getIcon() {
        return Assets.appleImage;
    }
}
