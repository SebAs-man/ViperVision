package com.github.sebasman.contracts.model;

import com.github.sebasman.contracts.vo.Position;

import java.util.Set;

/**
 * The FoodAPI interface defines the contract for food-related operations in the game.
 */
public interface IFoodAPI {
    /**
     * Places the food in a new random position, avoiding all the provided
     * occupiedSpots provided.
     * @param occupiedSpots A set of all positions that the food must avoid.
     * (including the snake's body and board obstacles).
     */
    void spawn(Set<Position> occupiedSpots);

    /**
     * Retrieves the score value associated with the food.
     * @return The score value of the food, which is added to the player's score when eaten.
     */
    int getScoreValue();

    /**
     * Retrieves the current position of the food.
     * @return The position of the food on the grid.
     */
    Position getPosition();
}
