package com.github.sebasman.contracts.model;

import com.github.sebasman.contracts.vo.Position;

import java.util.Set;

/**
 * The FoodAPI interface defines the contract for food-related operations in the game.
 */
public interface IFoodAPI {
    /**
     * Spawns food at a random position on the grid, ensuring it does not overlap with the snake's body.
     * @param snakeBody A list of positions representing the snake's body.
     */
    void spawn(Set<Position> snakeBody);

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
