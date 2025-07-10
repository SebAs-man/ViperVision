package com.github.sebasman.model.effects;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.effects.IEffect;

/**
 * Affects the growth process of the snake.
 */
public class GrowthEffect implements IEffect {
    private final int amount;

    /**
     * Create the effect to modify the growth of the snake.
     * @param amount The amount of growth of the snake's body.
     */
    public GrowthEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(IGameSession session) {
        session.getSnake().grow(this.amount);
    }
}
