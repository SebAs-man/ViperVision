package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.EffectRequestedEvent;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.vo.NotificationType;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.effects.InvulnerabilityEffect;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Represents an invulnerability star food in the game that can be consumed.
 */
public final class StarFood extends ExpirableFood {
    private static final long EFFECT_TIME = 5000; // 5 seconds
    private static final long LIFETIME = 10000; // 10 seconds

    /**
     * Creates an instance of the invulnerability star.
     * @param position The initial position of this invulnerability star.
     */
    public StarFood(Position position) {
        super(5, position, 1, LIFETIME);
    }

    @Override
    public void applyEffect(IGameSession session) {
        super.applyEffect(session);
        EventManager.getInstance().notify(new EffectRequestedEvent(new InvulnerabilityEffect(EFFECT_TIME)));
        EventManager.getInstance().notify(new NotificationRequestedEvent(
                "Unstoppable!!!", NotificationType.ACHIEVEMENT, 2500
        ));
    }

    @Override
    public boolean countsForRespawn() {
        return true;
    }

    @Override
    public PImage getIcon() {
        return Assets.speedSheetImage;
    }
}
