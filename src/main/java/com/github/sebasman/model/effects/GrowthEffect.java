package com.github.sebasman.model.effects;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.effects.IEffect;

/**
 * An effect that changes the snake's length by a given amount.
 * Implemented as a record for conciseness and immutability.
 * @param amount The number of segments to add (positive) or remove (negative).
 */
public record GrowthEffect(int amount) implements IEffect {
    @Override
    public void apply(IGameSession session) {
        session.getSnake().grow(this.amount);
    }
}
