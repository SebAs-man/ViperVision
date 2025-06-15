package com.github.sebasman.entities;

import com.github.sebasman.core.interfaces.model.FoodAPI;
import com.github.sebasman.core.vo.Position;
import com.github.sebasman.utils.GameConfig;
import com.github.sebasman.utils.Assets;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.*;

/**
 * The Food class represents the food in the game.
 */
public class FoodImpl implements FoodAPI {
    // Attributes
    private Position position;
    private final int scoreValue;
    // Random number generator to select a random position for the food
    private final Random random;

    /**
     * Constructor for the Food class.
     */
    public FoodImpl(int scoreValue, Position initialPosition) {
        this.scoreValue = Math.max(scoreValue, 1); // Ensure score value is at least 1
        Objects.requireNonNull(initialPosition);
        this.position = initialPosition;
        this.random = new Random();
    }

    @Override
    public void draw(PApplet context, Float interpolation) {
        if(this.position == null) return; // Ensure position is set before drawing
        PImage appleImage = Assets.appleImage;
        int x = this.position.x() * GameConfig.BOX_SIZE;
        int y = this.position.y() * GameConfig.BOX_SIZE;
        context.image(appleImage, x, y, GameConfig.BOX_SIZE, GameConfig.BOX_SIZE);
    }

    @Override
    public void spawn(Set<Position> snakeBody) {
        int gridWidth = GameConfig.GRID_WIDTH;
        int gridHeight = GameConfig.GRID_HEIGHT;
        int totalSpots = gridWidth * gridHeight;
        int availableSpots = totalSpots - snakeBody.size();

        if(availableSpots <= 0){
            System.out.println("¡Victory! You have eaten all the food!");
            this.position = null;
            return;
        }

        int targetEmptySpot = this.random.nextInt(availableSpots);
        int emptySpotCount = 0;

        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                Position currentPos = new Position(x, y);
                if (!snakeBody.contains(currentPos)) {
                    if (emptySpotCount == targetEmptySpot) {
                        this.position = currentPos;
                        return;
                    }
                    emptySpotCount++;
                }
            }
        }
    }

    @Override
    public int getScoreValue() {
        return this.scoreValue;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }
}
