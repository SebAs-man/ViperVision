package com.github.sebasman.contracts.model;

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
     * Returns the current food instance in the game.
     * @return The Food instance representing the food in the game.
     */
    IFoodAPI getFood();

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
     * Increments the score by a specified number of points.
     * @param points The number of points to add to the score.
     */
    void incrementScore(int points);
}
