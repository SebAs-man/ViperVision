package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.model.entities.IExpirable;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.entities.Food;

public abstract class ExpirableFood extends Food implements IExpirable {
    protected long timeLeft;

    /**
     * Constructor for food that can expire.
     * @param scoreValue    The score value of the food.
     * @param position      The initial position of the food.
     * @param growthValue   The growth value of the food.
     * @param lifetimeMillis The duration this food will exist before expiring.
     */
    public ExpirableFood(int scoreValue, Position position, int growthValue, long lifetimeMillis) {
        super(scoreValue, position, growthValue);
        this.timeLeft = lifetimeMillis;
    }

    @Override
    public void update(long elapsedTime) {
        this.timeLeft -= elapsedTime;
    }

    @Override
    public final boolean isExpired() {
        return this.timeLeft <= 0;
    }
}
