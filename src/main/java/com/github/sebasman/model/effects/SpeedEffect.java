package com.github.sebasman.model.effects;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.GameSpeedChangedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.effects.ITimedEffect;
import com.github.sebasman.model.config.ModelConfig;

/**
 * A timed effect that requests a temporary change in the game's tick speed.
 * It publishes an event that the PlayingState listens to.
 */
public class SpeedEffect implements ITimedEffect {
    private final float speed;
    private long timeLeft;

    /**
     * Constructs a SpeedEffect.
     * @param speed The value of snake speed.
     * @param durationMillis The duration of the effect.
     */
    public SpeedEffect(float speed, long durationMillis) {
        this.speed = speed;
        this.timeLeft = durationMillis;
    }

    @Override
    public void apply(IGameSession session) {
        // Publishes an event to request the speed change.
        EventManager.getInstance().notify(new GameSpeedChangedEvent(this.speed));
    }

    @Override
    public void update(long elapsedTime) {
        this.timeLeft -= elapsedTime;
    }

    @Override
    public void onFinish(IGameSession session) {
        // Publishes an event to revert the speed to normal.
        EventManager.getInstance().notify(new GameSpeedChangedEvent(ModelConfig.STARTING_FRAME_RATE));
    }

    @Override
    public boolean isFinished() {
        return this.timeLeft <= 0;
    }
}
