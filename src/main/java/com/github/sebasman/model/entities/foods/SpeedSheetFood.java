package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.EffectRequestedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.model.effects.SpeedEffect;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

import java.util.Random;

/**
 * Represents a speed sheet food in the game that can be consumed,
 * this can be good or bad.
 */
public final class SpeedSheetFood extends ExpirableFood {
    private static final long EFFECT_TIME = 8000; // 8 seconds
    private static final long LIFETIME  = 12000; // 12 seconds

    /**
     * reates an instance of the speed sheet
     * @param position The initial position of this speed sheet.
     */
    public SpeedSheetFood(Position position) {
        super(5, position, 0, LIFETIME);
    }

    @Override
    public void applyEffect(IGameSession session) {
        super.applyEffect(session);
        // The effect only applies if the player is human.
        boolean faster = new Random().nextBoolean();
        float speed = faster ? ModelConfig.STARTING_FRAME_RATE*1.5f : ModelConfig.STARTING_FRAME_RATE*0.7f; // 50% faster or 30% slower
        EventManager.getInstance().notify(new EffectRequestedEvent(new SpeedEffect(speed, EFFECT_TIME)));
    }

    @Override
    public PImage getIcon() {
        return Assets.speedSheetImage;
    }
}
