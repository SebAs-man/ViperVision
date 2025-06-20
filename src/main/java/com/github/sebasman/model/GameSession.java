package com.github.sebasman.model;

import com.github.sebasman.GameConfig;
import com.github.sebasman.contracts.model.IFoodAPI;
import com.github.sebasman.contracts.model.ISnakeAPI;
import com.github.sebasman.contracts.vo.Position;

/**
 * Contains all relevant status for a single game session.
 * Acts as the single source of truth for an ongoing game.
 */
public final class GameSession {
    private final ISnakeAPI snake;
    private final IFoodAPI food;
    private int score;

    public GameSession() {
        this.snake = new Snake(new Position(GameConfig.GRID_WIDTH/4, GameConfig.GRID_HEIGHT/2), 3);
        this.food = new Food(1, new Position(3*GameConfig.GRID_WIDTH/4, GameConfig.GRID_HEIGHT/2));
        this.score = 0;
    }

    // --- Getters ---

    /**
     * Returns the current snake instance in the game.
     * @return The Snake instance representing the snake in the game.
     */
    public ISnakeAPI getSnake() {
        return snake;
    }

    /**
     * Returns the current food instance in the game.
     * @return The Food instance representing the food in the game.
     */
    public IFoodAPI getFood() {
        return food;
    }

    /**
     * Returns the current score of the game.
     * @return The current score as an integer.
     */
    public int getScore() {
        return score;
    }

    // --- Setters ---

    /**
     * Resets the score to zero.
     */
    public void resetScore() {
        this.score = 0;
    }

    /**
     * Increments the score by a specified number of points.
     * @param points The number of points to add to the score.
     */
    public void incrementScore(int points) {
        if(points < 0) throw new IllegalArgumentException("Points cannot be negative.");
        this.score += points;
    }
}
