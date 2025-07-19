package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.entities.Food;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Represents an apple in the game that can be consumed by the snake.
 */
public final class AppleFood extends Food {
    /**
     * Creates an instance of the apple
     * @param initialPosition The initial position of the apple
     */
    public AppleFood(Position initialPosition){
        super(1, initialPosition, 1);
    }

    @Override
    public PImage getIcon() {
        return Assets.appleImage;
    }
}
