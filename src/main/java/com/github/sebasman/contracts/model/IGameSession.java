package com.github.sebasman.contracts.model;

import com.github.sebasman.contracts.model.effects.ITimedEffect;
import com.github.sebasman.contracts.model.entities.IBoardAPI;
import com.github.sebasman.contracts.model.entities.IFoodAPI;
import com.github.sebasman.contracts.model.entities.ISnakeAPI;

import java.util.List;
import java.util.Set;

/**
 * Defines the contract for a game session.
 * Sets out the methods necessary for the Presenter to interact with the game state.
 */
public interface IGameSession {
    /**
     * Returns the current snake instance in the game.
     * @return The Snake instance representing the snake in the game.
     */
    ISnakeAPI getSnake();

    /**
     * Returns the current list food instance in the game.
     * @return The list of food instances representing the food in the game.
     */
    Set<IFoodAPI> getFoods();

    /**
     * Return the current board instance in the game.
     * @return The Board instance represente the board in the game.
     */
    IBoardAPI getBoard();

    /**
     * Returns the current score of the game.
     * @return The current score as an integer.
     */
    int getScore();

    /**
     * Gets the list of active effects in the game session.
     * @return A list of active effects.
     */
    List<ITimedEffect> getActiveEffects();

    /**
     * Adds new temporary effects to the session.
     * @param effect The new active time effect.
     */
    void addTimedEffect(ITimedEffect effect);

    /**
     * Add a new food to the game
     * @param food The food has added
     */
    void addFood(IFoodAPI food);

    /**
     * Removes an existing meal in the game
     * @param food Food to be removed
     */
    void removeFood(IFoodAPI food);

    /**
     * Increments the score by a specified number of points.
     * @param points The number of points to add to the score.
     */
    void incrementScore(int points);
}
