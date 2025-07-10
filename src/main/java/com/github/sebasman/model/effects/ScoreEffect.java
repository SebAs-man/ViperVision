package com.github.sebasman.model.effects;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.ScoreUpdatedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.effects.IEffect;

/**
 * Affects the score of the current game session.
 */
public class ScoreEffect implements IEffect {
    private final int amount;

    /**
     * Create the effect to modify the score.
     * @param amount The modified amount of this.
     */
    public ScoreEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(IGameSession session) {
        session.incrementScore(this.amount);
        EventManager.getInstance().notify(new ScoreUpdatedEvent(session.getScore()));
    }
}
