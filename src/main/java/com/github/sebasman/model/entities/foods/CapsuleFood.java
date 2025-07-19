package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

import java.util.Random;

/**
 * Represents a growth capsule food in the game that can be consumed,
 * but the snake should be careful
 */
public final class CapsuleFood extends ExpirableFood {
    private static final int LIFETIME = 18000; // 18 seconds
    private static final int MIN_VALUE = 4;
    private static final int MAX_VALUE = 8;

    /**
     * Creates an instance of the capsule.
     * @param position The initial position of this capsule.
     */
    public CapsuleFood(Position position) {
        super(5, position, new Random().nextInt(MAX_VALUE - MIN_VALUE) + MIN_VALUE, LIFETIME);
    }

    @Override
    public PImage getIcon() {
        return Assets.capsuleImage;
    }
}
