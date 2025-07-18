package com.github.sebasman.model.effects;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.ScoreUpdatedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.effects.IEffect;

/**
 * An effect that modifies the player's score by a given amount.
 * Implemented as a record for conciseness and immutability.
 * @param amount The amount to add to the score (can be negative).
 */
public record ScoreEffect(int amount) implements IEffect {
    @Override
    public void apply(IGameSession session) {
        session.incrementScore(this.amount);
        // Notifies the UI about the change so the HUD can update.
        EventManager.getInstance().notify(new ScoreUpdatedEvent(session.getScore()));
    }
}
