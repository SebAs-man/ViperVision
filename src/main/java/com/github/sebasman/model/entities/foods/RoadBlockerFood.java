package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.EffectRequestedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.effects.SpawnObstaclesEffect;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Represents a road blocker food in the game that can be consumed.
 */
public final class RoadBlockerFood extends ExpirableFood {
    private static final long LIFETIME = 15000; // 15 seconds
    private static final int MIN_VALUE = 5;
    private static final int MAX_VALUE = 15;

    /**
     * reates an instance of the road blocker food.
     * @param position The initial position of this food.
     */
    public RoadBlockerFood(Position position) {
        super(1, position, 0, LIFETIME);
    }

    @Override
    public void applyEffect(IGameSession session) {
        super.applyEffect(session);
        EventManager.getInstance().notify(new EffectRequestedEvent(new SpawnObstaclesEffect(MIN_VALUE, MAX_VALUE, session)));
    }

    @Override
    public PImage getIcon() {
        return Assets.roadBlockerImage;
    }
}
