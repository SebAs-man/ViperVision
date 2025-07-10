package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.EffectRequestedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.effects.GrowthEffect;
import com.github.sebasman.model.effects.ScoreEffect;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Represents an apple in the game that can be consumed by the snake.
 */
public class AppleFood extends Food{
    /**
     * Creates an instance of the apple
     * @param initialPosition The initial position of the apple
     */
    public AppleFood(Position initialPosition){
        super(1, initialPosition, 1);
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
