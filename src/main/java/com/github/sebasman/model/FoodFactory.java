package com.github.sebasman.model;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.model.entities.IFoodAPI;
import com.github.sebasman.contracts.vo.NotificationType;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.model.entities.foods.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Factory responsible for creating and positioning different types of food
 * randomly on the board. Centralizes the spawn logic.
 */
public final class FoodFactory {
    private static final FoodFactory INSTANCE = new FoodFactory();
    private final Random random;

    /**
     * Creates a new instance of the food factory.
     */
    private FoodFactory() {
        this.random = new Random();
    }

    /**
     * Return the factory instance
     * @return Its instance
     */
    public static FoodFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new batch of food items. The number of items is random.
     * It enforces the rule that a negative food cannot spawn alone.
     * @param occupiedSpots The set of all tiles that must be avoided.
     * @param existingFoods The list of foods currently on the board, to avoid stacking.
     * @return A list of new food items to be added to the game session.
     */
    public Set<IFoodAPI> createFoodBatch(Set<Position> occupiedSpots, Set<IFoodAPI> existingFoods){
        Set<IFoodAPI> batch = new HashSet<>();
        int amountToSpawn = random.nextInt(4) + 1; // Between 1 and 4
        // Collect all currently occupied spots
        Set<Position> allOccupiedSpots = new HashSet<>(occupiedSpots);
        allOccupiedSpots.addAll(existingFoods.stream().map(IFoodAPI::getPosition).collect(Collectors.toSet()));
        for(int i = 0; i < amountToSpawn; i++){
            IFoodAPI newFood = this.createRandomFood(allOccupiedSpots);
            if(newFood != null){
                batch.add(newFood);
                allOccupiedSpots.add(newFood.getPosition());
            }
        }

        // --- RULE ENFORCEMENT ---

        boolean hasPositiveFood = batch.stream().anyMatch(IFoodAPI::countsForRespawn);
        if(!hasPositiveFood){
            boolean anyPositiveOnBoard = existingFoods.stream().anyMatch(IFoodAPI::countsForRespawn);
            if(!anyPositiveOnBoard){
                while(true){
                    IFoodAPI emergencyApple = this.createRandomFood(allOccupiedSpots);
                    if(emergencyApple != null && emergencyApple.countsForRespawn()){
                        batch.add(emergencyApple);
                        break;
                    }
                }
            }
        }

        return batch;
    }

    /**
     * Creates a single random food item based on probability.
     * @param occupiedSpots The set of all tiles that must be avoided.
     * @return A new food item to be added to the game session.
     */
    private IFoodAPI createRandomFood(Set<Position> occupiedSpots){
        Position spawnPosition = this.findRandomEmptySpot(occupiedSpots);
        if(spawnPosition == null) return null;

        // --- Probability Logic ---

        double chance = random.nextDouble();  // A random value between 0.0 and 1.0
        if(chance < 0.005) return new StarFood(spawnPosition);
        else if(chance < 0.025) return new RoadBlockerFood(spawnPosition);
        else if(chance < 0.055) return new SpeedSheetFood(spawnPosition);
        else if (chance < 0.12) return new GoldenAppleFood(spawnPosition);
        else if(chance < 0.405) return new PoisonFood(spawnPosition);
        return new AppleFood(spawnPosition);
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

        EventManager.getInstance().notify(new NotificationRequestedEvent(
                "UPS! An error occurred while searching for the food",
                NotificationType.ERROR, 10000
        ));
        return null;
    }
}
