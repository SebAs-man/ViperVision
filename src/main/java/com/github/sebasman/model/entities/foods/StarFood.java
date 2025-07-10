package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.vo.NotificationType;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Represents an invulnerability star food in the game that can be consumed.
 */
public class StarFood extends Food{
    /**
     * Creates an instance of the invulnerability star.
     * @param position The initial position of this invulnerability star.
     */
    private StarFood(Position position) {
        super(5, position, 1);
    }

    @Override
    public void applyEffect(IGameSession session) {
        EventManager.getInstance().notify(new NotificationRequestedEvent(
                "Star consumed! Now you are invulnerable!", NotificationType.ACHIEVEMENT, 1000
        ));
    }

    @Override
    public PImage getIcon() {
        return Assets.appleImage;
    }
}
