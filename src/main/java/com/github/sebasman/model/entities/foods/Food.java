package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.model.entities.IFoodAPI;
import com.github.sebasman.contracts.vo.Position;

/**
 * Abstract base class for all types of food in the game.
 * Contains the position, which is common to all meals, and defines
 * the contracts that the subclasses must implement.
 */
public abstract class Food implements IFoodAPI {
    protected Position position;
    protected final int scoreValue;
    protected final int growthValue;

    /**
     * Create food with its basic elements
     * @param scoreValue The nutritional value of food
     * @param position The initial position of the food, only if applicable.
     * @param growthValue The amount of snake growth
     */
    public Food(int scoreValue, Position position, int growthValue) {
        this.scoreValue = scoreValue;
        this.position = position;
        this.growthValue = growthValue;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }
}
