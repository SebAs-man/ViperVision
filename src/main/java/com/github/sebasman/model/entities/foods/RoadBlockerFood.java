package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.vo.NotificationType;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Represents a road blocker food in the game that can be consumed.
 */
public class RoadBlockerFood extends Food{

    private static final int MIN_VALUE = 5;
    private static final int MAX_VALUE = 15;

    /**
     * reates an instance of the road blocker food.
     * @param position The initial position of this food.
     */
    public RoadBlockerFood(Position position) {
        super(0, position, 0);
    }

    @Override
    public void applyEffect(IGameSession session) {
        int numObstacles = 5;
        EventManager.getInstance().notify(new NotificationRequestedEvent(
                "Road blocker consumed! be careful (" + numObstacles + ")", NotificationType.WARNING, 1000
        ));
    }

    @Override
    public PImage getIcon() {
        return Assets.appleImage;
    }
}
