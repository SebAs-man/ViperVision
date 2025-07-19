package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.entities.Food;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Represents a golden apple in the game that can be consumed by the snake and receive unique benefits
 */
public final class GoldenAppleFood extends Food {
    /**
     * Creates an instance of the golden apple
     * @param position the initial position of the golden apple
     */
    public GoldenAppleFood(Position position) {
        super(10, position, 1);
    }

    @Override
    public boolean countsForRespawn() {
        return true;
    }

    @Override
    public PImage getIcon() {
        return Assets.goldenAppleImage;
    }
}
