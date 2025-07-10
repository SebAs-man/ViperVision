package com.github.sebasman.model.entities.foods;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.model.entities.IFoodAPI;
import com.github.sebasman.contracts.vo.NotificationType;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;

import java.util.Random;
import java.util.Set;

/**
 * Factory responsible for creating and positioning different types of food
 * randomly on the board. Centralizes the spawn logic.
 */
public final class FoodFactory {
    private final Random random;

    /**
     * Creates a new instance of the food factory
     */
    public FoodFactory() {
        this.random = new Random();
    }

    /**
     * Creates a new food object of a random type in a valid position.
     * @param occupiedSpots The set of all boxes to avoid.
     * @return a new IFoodAPI instance, or null if there is no space.
     */
    public IFoodAPI createRandomFood(Set<Position> occupiedSpots){
        Position position = this.findRandomEmptySpot(occupiedSpots);
        if(position == null) return null;

        // Probability logic to decide which food to create.
        double chance = random.nextDouble();
        if (chance < 0.9) {
            return new AppleFood(position);
        } else {
            return new PoisonFood(position);
        }
    }

    /**
     * Find a random empty square on the board.
     * @param occupiedSpots The set of squares to avoid.
     * @return a valid and empty Position, or null if the board is full.
     */
    private Position findRandomEmptySpot(Set<Position> occupiedSpots){
        int gridWidth = ModelConfig.GRID_WIDTH;
        int gridHeight = ModelConfig.GRID_HEIGHT;
        int totalSpots = gridWidth * gridHeight;
        int availableSpots = totalSpots - occupiedSpots.size();

        if(availableSpots <= 0){
            EventManager.getInstance().notify(new NotificationRequestedEvent("Victory! There is no more room for food!",
                    NotificationType.ACHIEVEMENT, 6000));
            return null;
        }

        int targetEmptySpot = random.nextInt(availableSpots);
        int emptySpotCount = 0;

        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                Position currentPos = new Position(x, y);
                if (!occupiedSpots.contains(currentPos)) {
                    if (emptySpotCount == targetEmptySpot) {
                        return currentPos;
                    }
                    emptySpotCount++;
                }
            }
        }

        // Strange case
        EventManager.getInstance().notify(new NotificationRequestedEvent(
                "UPS! An error occurred while searching for the food",
                NotificationType.ERROR, 10000
        ));
        return null;
    }
}
