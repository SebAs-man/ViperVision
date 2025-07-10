package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.vo.NotificationType;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Represents a speed sheet food in the game that can be consumed,
 * this can be good or bad.
 */
public class SpeedSheetFood extends Food {

    private static final int MIN_RATE = 1;
    private static final int MAX_RATE = 5;

    /**
     * reates an instance of the speed sheet
     * @param position The initial position of this speed sheet.
     */
    public SpeedSheetFood(Position position) {
        super(1, position, 1);
    }

    @Override
    public void applyEffect(IGameSession session) {
        boolean effect = true;
        int speed = 5;
        if (effect) {
            EventManager.getInstance().notify(new NotificationRequestedEvent(
                    "Consumed speed sheet! Reduced speed (-" + speed + ")", NotificationType.INFO, 1000
            ));
        } else{
            EventManager.getInstance().notify(new NotificationRequestedEvent(
                    "Consumed speed sheet! Increased speed (+" + speed + ")", NotificationType.INFO, 1000
            ));
        }
    }

    @Override
    public PImage getIcon() {
        return Assets.appleImage;
    }
}
