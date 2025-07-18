package com.github.sebasman.contracts.model.entities;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.vo.FoodCategory;
import com.github.sebasman.contracts.vo.Position;
import processing.core.PImage;

/**
 * The FoodAPI interface defines the contract for food-related operations in the game.
 */
public interface IFoodAPI {
    /**
     * Applies the special effect of this food for the active game session.
     * @param session The game session on which the effect will be applied.
     */
    void applyEffect(IGameSession session);

    /**
     * Retrieves the current position of the food.
     * @return The position of the food on the grid.
     */
    Position getPosition();

    /**
     * Returns the category of this food item.
     * @return The {@link FoodCategory} of the food.
     */
    FoodCategory getCategory();

    /**
     * Returns the specific image (asset) representing this type of food.
     * @return a PImage object to be drawn.
     */
    PImage getIcon();
}
