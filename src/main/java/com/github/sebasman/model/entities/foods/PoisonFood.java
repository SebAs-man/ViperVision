package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.vo.FoodCategory;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Represents a poison in the game that cannot be by the snake unless it is prejudice.
 */
public final class PoisonFood extends ExpirableFood {
    private static final long LIFETIME = 10000; // 10 seconds

    /**
     * Creates an instance of the poison
     * @param position the initial position of the poison
     */
    public PoisonFood(Position position) {
        super(-1, position, -1, LIFETIME);
    }

    @Override
    public FoodCategory getCategory() {
        return FoodCategory.NEGATIVE;
    }

    @Override
    public PImage getIcon() {
        return Assets.poisonImage;
    }
}
