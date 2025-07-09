package com.github.sebasman.model;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.model.IFoodAPI;
import com.github.sebasman.contracts.vo.NotificationType;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;

import java.util.*;

/**
 * The Food class represents the food in the game.
 */
public class Food implements IFoodAPI {
    // Attributes
    private Position position;
    private final int scoreValue;
    // Random number generator to select a random position for the food
    private final Random random;

    /**
     * Constructor for the Food class.
     */
    public Food(int scoreValue, Position initialPosition) {
        this.scoreValue = Math.max(scoreValue, 1); // Ensure score value is at least 1
        Objects.requireNonNull(initialPosition);
        this.position = initialPosition;
        this.random = new Random();
    }

    @Override
    public void spawn(Set<Position> occupiedSpots) {
        int gridWidth = ModelConfig.GRID_WIDTH;
        int gridHeight = ModelConfig.GRID_HEIGHT;
        int totalSpots = gridWidth * gridHeight;
        int availableSpots = totalSpots - occupiedSpots.size();

        if(availableSpots <= 0){
            EventManager.getInstance().notify(new NotificationRequestedEvent("Victory! There is no more room for food!",
                    NotificationType.ACHIEVEMENT, 6000));
            this.position = null;
            return;
        }

        int targetEmptySpot = this.random.nextInt(availableSpots);
        int emptySpotCount = 0;

        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                Position currentPos = new Position(x, y);
                if (!occupiedSpots.contains(currentPos)) {
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
