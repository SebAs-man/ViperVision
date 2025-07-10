package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.EffectRequestedEvent;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.vo.NotificationType;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.effects.GrowthEffect;
import com.github.sebasman.model.effects.ScoreEffect;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Represents a poison in the game that cannot be by the snake unless it is prejudice.
 */
public class PoisonFood extends Food {
    /**
     * Creates an instance of the poison
     * @param position the initial position of the poison
     */
    public PoisonFood(Position position) {
        super(-1, position, 0);
    }

    @Override
    public void applyEffect(IGameSession session) {
        EventManager.getInstance().notify(new EffectRequestedEvent(
                new ScoreEffect(super.scoreValue)
        ));
        EventManager.getInstance().notify(new EffectRequestedEvent(
                new GrowthEffect(super.growthValue)
        ));
    }

    @Override
    public PImage getIcon() {
        return Assets.appleImage;
    }
}
